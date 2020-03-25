package com.littlehui.ftrade.mail.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by littlehui on 2020/3/24.
 */
public class MailSenderThreadHelper {

    public static AtomicInteger totalThread = new AtomicInteger(1);

    public static AtomicInteger finishedThread = new AtomicInteger(0);

    public static AtomicInteger mailCountPerThread = new AtomicInteger(90);

    public static volatile AtomicInteger sendedMailCount = new AtomicInteger(0);


    public static volatile AtomicInteger liteSendCount = new AtomicInteger(0);

    public static volatile AtomicInteger failedCount = new AtomicInteger(0);


    public static void init(Integer totalThread, Integer mailCountPerThread, Integer sendCountLimit) {
        MailSenderThreadHelper.totalThread.set(totalThread);
        MailSenderThreadHelper.mailCountPerThread.set(mailCountPerThread);
        MailSenderThreadHelper.liteSendCount.set(sendCountLimit);

    }

    public static boolean allFinishFlag() {
        return totalThread.intValue() == finishedThread.intValue();
    }

    public static void adOneFinish() {
        finishedThread.incrementAndGet();
    }

    public static void addOneSendedMail() {
        sendedMailCount.incrementAndGet();
    }

    public static int leftMailSend() {
        return totalThread.intValue() * mailCountPerThread.intValue() - sendedMailCount.intValue();
    }
}
