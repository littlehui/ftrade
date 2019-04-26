package com.littlehui.ftrade.web.config;

import com.u17173.treasurebox.auth.AuthApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 扫描公用权限包
 */
@Component
@Configuration
@ComponentScan(basePackageClasses = AuthApplication.class)
public class TreasureConfig {


}
