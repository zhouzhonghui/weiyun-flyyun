package cn.fly.yun.config.interceptor;

import cn.fly.yun.base.TransLog;
import cn.fly.yun.config.exceptions.BusinessException;
import cn.fly.yun.config.utils.DateTimeUtils;
import cn.fly.yun.config.utils.ThreadLocalUtils;
import cn.fly.yun.handle.RedisHandle;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Configuration
public class HeaderInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeaderInterceptor.class);

    @Resource
    RedisHandle redisHandle;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String locale = request.getHeader("locale");
        logger.info("---------------------------------locale= " + locale);
        if (!StringUtils.isEmpty(locale)) {
            ThreadLocalUtils.setLocalLanaguage(locale);
        }

        TransLog transLog = initTransLog(request);

        ThreadLocalUtils.setLocalTranslog(transLog);

        return super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        TransLog transLog = ThreadLocalUtils.getLocalTranslog();
        if (null == transLog)
            transLog = initTransLog(request);

        String outDate = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS);
        long timeConsume = DateTimeUtils.msBetween(DateTimeUtils.getStringToDateTime(transLog.getInDate(), DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS), DateTimeUtils.getStringToDateTime(outDate, DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS));
        transLog.setTimeConsume(timeConsume);
        transLog.setOutDate(outDate);
        if (null != ex) {
            transLog.setResponseData(ex.toString());
        }
        ThreadLocalUtils.resetLocalTranslog();
        ThreadLocalUtils.resetLocalLanaguage();

        /**
         * 流水发送
         */

    }

    private TransLog initTransLog(HttpServletRequest request) throws Exception{
        TransLog transLog = new TransLog();
        String ip = request.getRemoteAddr();
        String serviceName = request.getRequestURI();
        String token = request.getHeader("wybb-token");
        if (StringUtils.hasText(token)) {
            String value = (String) redisHandle.get(token);
            if (StringUtils.hasText(value)) {
                String[] strArr = value.split("_");
                if (2 == strArr.length) {
                    transLog.setRedisMobile(strArr[0]);
                    transLog.setMemberId(Long.parseLong(strArr[1]));
                }
            }
        }

        if(serviceName.indexOf("checkToken")>0 && !StringUtils.hasText(transLog.getRedisMobile())){
            throw new BusinessException("member.is.not.login") ;
        }


        String requestLocale = request.getHeader("locale");
        if (StringUtils.hasText(requestLocale)) {
            ThreadLocalUtils.setLocalLanaguage(requestLocale);
        }
        else
            ThreadLocalUtils.setLocalLanaguage("china");
        String userAgent = request.getHeader("User-Agent");
        String appNameEn = request.getHeader("appNameEn");
        String idfa = request.getHeader("idfa");
        String inDate = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS);
        String requestTimestamp = request.getHeader("timestamp");
        Map map = request.getParameterMap();
        String tranDate = inDate.substring(0, 8);
        String seq = tranDate + String.format("%08d", redisHandle.incrementMapLong("tranDate", tranDate, 1));

        transLog.setIp(ip);
        transLog.setServiceName(serviceName);
        transLog.setRequestLocale(requestLocale);
        transLog.setUserAgent(userAgent);
        transLog.setAppNameEn(appNameEn);
        transLog.setIdfa(idfa);
        transLog.setInDate(inDate);
        transLog.setRequestTimestamp(requestTimestamp);
        transLog.setRequestData(JSON.toJSONString(map));
        transLog.setSeq(seq);
        transLog.setToken(token);

        return transLog;
    }
}
