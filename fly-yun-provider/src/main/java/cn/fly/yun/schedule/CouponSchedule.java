package cn.fly.yun.schedule;

import cn.fly.yun.domain.WybbCoupon;
import cn.fly.yun.domain.WybbCouponThird;
import cn.fly.yun.mapper.WybbCouponMapper;
import cn.fly.yun.mapper.WybbCouponThirdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class CouponSchedule {
    @Autowired
    CouponService couponService ;

    @Scheduled(fixedRate = 900000000)
    public void addCoupon() {
        for(int i = 0;i<2800;i++) {
            try {
                couponService.addCoupon(i) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
