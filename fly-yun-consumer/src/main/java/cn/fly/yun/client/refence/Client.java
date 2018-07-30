package cn.fly.yun.client.refence;

import cn.fly.yun.domain.QuotesTransactionReq;
import cn.fly.yun.service.NewsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.fly.yun"})
@ImportResource(locations = {"classpath*:application-.xml"})
public class Client {
    static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(Client.class, args);

        NewsInfoService bean = run.getBean(NewsInfoService.class);
        bean.quotesTransaction(new QuotesTransactionReq());

    }
}
