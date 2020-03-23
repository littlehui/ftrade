package com.littlehui.ftrade.tests.services;

import com.littlehui.ftrade.mail.service.MailImportService;
import com.littlehui.ftrade.web.FtradeWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description 从ConsumerDetail转移到AvaliableConsumerDetail
 * @ClassName ConsumerDumpTest
 * @Author littlehui
 * @Date 2020/3/23 15:59
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FtradeWebApplication.class)
@ActiveProfiles("local")
public class ConsumerDumpTest {

    @Autowired
    MailImportService mailImportService;

    @Test
    public void consumerDetailToAvaliableComsumerDetail() {
        mailImportService.consumerDetailToAvaliableComsumerDetail();
    }
}
