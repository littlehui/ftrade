package com.littlehui.ftrade.mail.config;

import cn.hutool.setting.dialect.Props;
import com.littlehui.ftrade.mail.bean.MailUser;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Properties;

/**
 * @Description TODO
 * @ClassName MailConfig
 * @Author littlehui
 * @Date 2019/4/19 18:17
 * @Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "mail.config")
@Data
public class MailConfig {

    List<MailUser> listUsers;

    String host;

    String mailTemplateHtml;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        Properties mailProperty = new Props();
        mailProperty.put("mail.smtp.auth", true);
        mailProperty.put("mail.smtp.timeout", 25000);
        javaMailSender.setJavaMailProperties(mailProperty);
        return javaMailSender;
    }

}
