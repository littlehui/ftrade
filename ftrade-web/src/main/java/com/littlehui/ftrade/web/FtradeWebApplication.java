package com.littlehui.ftrade.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description TODO
 * @ClassName FtradeWebApplication
 * @Author littlehui
 * @Date 2019/4/2 15:33
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.littlehui.ftrade"})
public class FtradeWebApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FtradeWebApplication.class);
        application.run(args);
    }
}
