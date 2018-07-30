package cn.fly.yun.client.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableSwagger2
public class Swagger2Config {
//    @Bean
//    public Docket createRestApi() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
////        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
////        pars.add(tokenPar.build());
////        tokenPar.name("timestamp").description("时间戳").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
////        pars.add(tokenPar.build());
////        tokenPar.name("md5").description("签名值").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
////        pars.add(tokenPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.fly.yun.client.controller"))
//                .paths(PathSelectors.regex("/app/.*"))
//                .build()
//                .globalOperationParameters(pars);
//    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("微云服务管理后台接口和测试")
//                .description("这是一个给app端人员调用server端接口的测试文档与平台")
//                .version("1.0.0")
//                .build();
//    }
}
