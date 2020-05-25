package com.littlehui.ftrade.mail.service;

import com.littlehui.ftrade.mail.bean.MailUser;
import com.littlehui.ftrade.mail.config.MailConfig;
import org.apache.log4j.Logger;

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

    public MailSenderContext(MailService mailService, MailConfig mailConfig, String batch) {
        this.mailService = mailService;
        this.mailConfig = mailConfig;
        this.batch = batch;
    }

    public void startSendMailTask() {
        List<Thread> mailSendThreads = new ArrayList<Thread>();
        List<MailUser> mailUsers = mailConfig.getListUsers();

        for (MailUser userBean : mailUsers) {
            Thread thread = new MailSendThread(mailService, userBean.getUser(), userBean.getPassword(), "邮箱：" + userBean.getUser());
            mailSendThreads.add(thread);
        }

        for (Thread mailSenderThread : mailSendThreads) {
            mailSenderThread.start();
        }
    }

    public class MailSendThread extends Thread {

        Logger logger = Logger.getLogger(MailSendThread.class);

        MailService mailService;

        String password;

        String username;

        public MailSendThread(MailService mailService, String username, String password, String threadName) {
            this.mailService = mailService;
            this.username = username;
            this.password = password;
            this.setName(threadName);
        }

        @Override
        public void run() {
            logger.info(this.getName() + "开始发送邮件。");
            for (int i = 0; i<MailSenderThreadHelper.mailCountPerThread.intValue(); i++) {
                //mailService.sendEmailInner(1, MailSenderThreadHelper.liteSendCount.intValue(), username, password, batch);
                Long randLong = new Random().nextLong();
                Long randMills = randLong%(60 * 1000);
                Long sleepMills = Math.abs(randMills);
                //Long nextMills = currentMills + Math.abs(randMills);
                try {
                    this.sleep(sleepMills);
                    //Thread.currentThread().sleep(sleepMills);
                    logger.info("" + this.getName() + "开始发第" + (i + 1) + "封邮件。总共需要发：" + MailSenderThreadHelper.mailCountPerThread.intValue() + "封。");
                    mailService.sandBatchOneEmailInner(MailSenderThreadHelper.liteSendCount.intValue(), username, password, batch);
                    logger.info("" + this.getName() + "完成第" + (i + 1) + "封邮件。总共需要发："
                            + MailSenderThreadHelper.mailCountPerThread.intValue()
                            + "封。还剩："
                            + (MailSenderThreadHelper.mailCountPerThread.intValue() - (i+1)) + "封");
                    //sleep 减少cpu开销
                    //this.sleep(sleepMills);
                } catch (Exception e) {
                    //e.printStackTrace();
                    logger.error(this.getName() + "发送邮件异常。" + e.getCause(), e);
                    MailSenderThreadHelper.failedCount.incrementAndGet();
                }
                MailSenderThreadHelper.addOneSendedMail();
                logger.info("总共已发：" + MailSenderThreadHelper.sendedMailCount.intValue() + "封，还剩：" + MailSenderThreadHelper.leftMailSend() + "封未发。");
            }
            logger.info(this.getName() + "结束发送邮件。");
            logger.info("失败总数：" + MailSenderThreadHelper.failedCount.intValue());

            MailSenderThreadHelper.adOneFinish();
        }
    }
}
