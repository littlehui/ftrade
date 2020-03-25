package com.littlehui.ftrade.tests.services;

import com.littlehui.ftrade.mail.dao.InitialConsumerDetailManager;
import com.littlehui.ftrade.mail.service.MailSenderContext;
import com.littlehui.ftrade.mail.service.MailSenderThreadHelper;
import com.littlehui.ftrade.mail.service.MailService;
import com.littlehui.ftrade.web.FtradeWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by littlehui on 2018/9/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FtradeWebApplication.class)
@ActiveProfiles("local")
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Autowired
    InitialConsumerDetailManager initialConsumerDetailManager;

    @Test
    public void validAllEmailTests() {
/*        mailService.validAllEmailBatch("61", 10);
        mailService.validAllEmailBatch("62", 10);
        mailService.validAllEmailBatch("63", 10);*/
/*        mailService.validAllEmailBatch("64", 10);
        mailService.validAllEmailBatch("65", 10);*/
/*
        mailService.validAllEmailBatch("66", 10);
*/
/*        mailService.validAllEmailBatch("67", 10);
        mailService.validAllEmailBatch("68", 10);
        mailService.validAllEmailBatch("69", 10);*/
/*        mailService.validAllEmailBatch("70", 10);
        mailService.validAllEmailBatch("71", 10);
        mailService.validAllEmailBatch("72", 10);
        mailService.validAllEmailBatch("73 ", 10);
        mailService.validAllEmailBatch("74", 10);
        mailService.validAllEmailBatch("75", 10);
        mailService.validAllEmailBatch("76", 10);*/
/*        mailService.validAllEmailBatch("77", 10);*/
/*        mailService.validAllEmailBatch("78", 10);
        mailService.validAllEmailBatch("79", 10);*/
        mailService.validAllEmailBatch("73", 10);
    }

    @Test
    public void testQUery() {
        initialConsumerDetailManager.findByBatchAndNonTested("67");
    }

    @Test
    public void testMailWithHtml() {
        String from = "littlehui@idaymay.com";
        mailService.simpleMailWithHtml("812699887@qq.com", from, "testSubject", "testName", "littlehui@idaymay.com", "7397495keep");
    }


    @Test
    public void sendMailTests() {
        MailSenderThreadHelper.init(10,2, 2);
        mailService.sendMailBatch("1");
        while (!MailSenderThreadHelper.allFinishFlag()) {
        }
    }
}
