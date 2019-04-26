package com.littlehui.ftrade.tests.services;

import com.littlehui.ftrade.mail.service.MailService;
import com.littlehui.ftrade.web.FtradeWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by littlehui on 2018/9/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FtradeWebApplication.class)
@ActiveProfiles("local")
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void validAllEmailTests() {
        mailService.validAllEmailBatch("61");
    }

}
