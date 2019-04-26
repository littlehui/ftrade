package com.littlehui.ftrade.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @ClassName AppConfig
 * @Author littlehui
 * @Date 2018/12/14 15:13
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "")
@Data
public class AppConfig {

}
