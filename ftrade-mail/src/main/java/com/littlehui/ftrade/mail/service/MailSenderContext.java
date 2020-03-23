package com.littlehui.ftrade.mail.service;

import com.littlehui.ftrade.mail.bean.MailUser;
import com.littlehui.ftrade.mail.config.MailConfig;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by littlehui on 2019/4/30.
 */
public class MailSenderContext {

    private MailService mailService;

    private MailConfig mailConfig;

    private String batch;

    public static ThreadLocal<Integer> liteSendCount = new ThreadLocal<>();

    public MailSenderContext(MailService mailService, MailConfig mailConfig, String batch) {
        this.mailService = mailService;
        this.mailConfig = mailConfig;
        this.batch = batch;
    }

    public void doSendMail() {
        if (liteSendCount.get() == null) {
            liteSendCount.set(1);
        }
        List<Thread> mailSendThreads = new ArrayList<Thread>();
        List<MailUser> mailUsers = mailConfig.getListUsers();

        for (MailUser userBean : mailUsers) {
            Thread thread = new MailSendThread(mailService, userBean.getUser(), userBean.getPassword());
            mailSendThreads.add(thread);
        }

        for (Thread mailSenderThread : mailSendThreads) {
            mailSenderThread.run();
        }
    }

    public class MailSendThread extends Thread {

        MailService mailService;

        String password;

        String username;

        public MailSendThread(MailService mailService, String username, String password) {
            this.mailService = mailService;
            this.username = username;
            this.password = password;
        }

        @Override
        public void run() {
            for (int i = 0; i<90; i++) {
                mailService.sendEmailInner(1,liteSendCount.get(), username, password, batch);
                Long randLong = new Random().nextLong();
                Long randMills = randLong%(60 * 1000);
                Long currentMills = System.currentTimeMillis();
                Long nextMills = currentMills + Math.abs(randMills);
                while (true) {
                    Long newCurrnetMills = System.currentTimeMillis();
                    if (newCurrnetMills > nextMills) {
                        mailService.sendEmailInner(1, liteSendCount.get(), username, password, batch);
                        break;
                    }
                }
            }
        }
    }
}
