package com.littlehui.ftrade.mail.service;

import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;
import com.littlehui.ftrade.mail.bean.InvaliableConsumerDetail;
import com.littlehui.ftrade.mail.config.MailConfig;
import com.littlehui.ftrade.mail.dao.AvaliableConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.ConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InitialConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InvaliableConsumerDetailManager;
import com.u17173.treasurebox.utils.object.ObjectUtils;
import com.u17173.treasurebox.utils.query.Paged;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @ClassName MailService
 * @Author littlehui
 * @Date 2019/4/25 16:32
 * @Version 1.0
 **/
@Service
public class MailService {

    static AtomicInteger alreadNum = new AtomicInteger(0);

    @Autowired
    InitialConsumerDetailManager initialConsumerDetailManager;

    @Autowired
    ConsumerDetailManager consumerDetailManager;

    @Autowired
    InvaliableConsumerDetailManager invaliableConsumerDetailManager;

    @Autowired
    AvaliableConsumerDetailManager avaliableConsumerDetailManager;

    @Autowired
    MailConfig mailConfig;

    @Autowired
    JavaMailSenderImpl mailSender;

    private static final String FULL_NAME = "_FULL_NAME_";

    public void sendMailBatch(String batch) {
        MailSenderContext mailSenderContext = new MailSenderContext(this, mailConfig, batch);
        mailSenderContext.doSendMail();
    }

    public void sendEmailInner(Integer limit, Integer limitCount, String mail, String password, String batch) {
        Paged<AvaliableConsumerDetail> page = null;
        if (batch != null) {
            page = avaliableConsumerDetailManager.queryConsumerDetailsBatch(1, limit, limitCount, batch);
        } else {
            page = avaliableConsumerDetailManager.queryConsumerDetails(1, limit, limitCount);
        }
        if (page.getListData() != null && page.getListData().size() > 0)
            for (AvaliableConsumerDetail consumerDetail : page.getListData()) {
                String toName = consumerDetail.getMan();
                if (toName == null || "".equals(toName)) {
                    toName = consumerDetail.getCompanyName();
                }
                String subject = "Re: " + consumerDetail.getMan();
                boolean result = this.simpleMailWithHtml(consumerDetail.getMail(), mail, subject, toName, mail, password);
                if (result) {
                    this.addSendCount(consumerDetail);
                }
            }
    }

    public void addSendCount(AvaliableConsumerDetail consumerDetail) {
        int sendCount = consumerDetail.getSendCount() == null ? 1 : consumerDetail.getSendCount() + 1;
        consumerDetail.setSendCount(sendCount);
        consumerDetail.setSendTime(new Timestamp(System.currentTimeMillis()));
        avaliableConsumerDetailManager.save(consumerDetail);
    }

    public boolean simpleMailWithHtml(String sendToAddress, String sendFromMailAddress, String subect, String toName, String username, String password) {
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        String context = mailConfig.getMailTemplateHtml().replaceFirst(FULL_NAME, toName);
        //发送带格式的邮件
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper	= new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(sendFromMailAddress, "lora");
            //helper.setCc("lora@idaymay.com");
            helper.setCc("emily@idaymay.com");
            //helper.setFrom(sendFromMailAddress);
            helper.setTo(sendToAddress);
            helper.setSubject(subect);
            helper.setText(context, true);   //内容
            //添加一个附件
            //FileDataSource file1 = new FileDataSource(new File("E:\\Edisk\\java\\spring_Cchop8.rar"));
            //helper.addAttachment(file1.getName(), file1);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //avaliableConsumerDetailManager.removeByMail(sendToAddress);
            return false;
        }
    }

    private void mailToInvaliable(String mail) {
       //AvaliableConsumerDetail avaliableConsumerDetail = avaliableConsumerDetailManager.getBymail(mail);
       //InvaliableConsumerDetail invaliableConsumerDetail = ObjectUtils.convertObj(avaliableConsumerDetail, InvaliableConsumerDetail.class);
       //invaliableConsumerDetail.setDeleteFlag();
       //invaliableConsumerDetailManager.save(invaliableConsumerDetail);
    }

    public void validAllEmailBatch(String batch, int thread) {
        List<InitialConsumerDetail> initialConsumerDetails = initialConsumerDetailManager.findByBatchAndNonTested(batch);
        int markTotal = thread == 0 ? 1 : thread;
        for (int mark = 0; mark<markTotal; mark++) {
            MailCheckThread mailCheckThread = new MailCheckThread(this, initialConsumerDetails, mark);
            mailCheckThread.start();
        }
        while (true) {
            if (MailService.alreadNum.intValue() == markTotal) {
                MailService.alreadNum = new AtomicInteger(0);
                break;
            }
        }
    }

    private class MailCheckThread extends Thread {

        MailService mailService;

        List<InitialConsumerDetail> companyDetails;

        int mark;

        Logger log = Logger.getLogger(MailCheckThread.class);

        public MailCheckThread(MailService mailService,List<InitialConsumerDetail> companyDetails, int mark) {
            this.mailService = mailService;
            this.companyDetails = companyDetails;
            this.mark = mark;
        }

        @Override
        public void run() {
            int i = 1;
            Long startMills = System.currentTimeMillis();
            for (InitialConsumerDetail companyDetail : companyDetails) {
                String idString = companyDetail.getId();
                String hexId = idString.substring(idString.length() - 3, idString.length() - 1);
                Integer id = Integer.parseUnsignedInt(hexId, 16);
                if (id%5 == mark) {
                    log.info("mark = " + mark + "第" + i + "个开始");
                    log.info("mark = " + mark + "验证邮箱：" + companyDetail.getMail());
                    Boolean result = MailValid.valid(companyDetail.getMail(), "idaymay.com");
                    log.info("mark = " + mark + "验证邮箱：" + companyDetail.getMail() + "结果：" + result);
                    log.info("mark = " + mark + "第" + i + "个结束");
                    companyDetail.setTestedFlag(true);
                    if (result) {
                        AvaliableConsumerDetail avaliableConsumerDetail = ObjectUtils.convertObj(companyDetail, AvaliableConsumerDetail.class);
                        avaliableConsumerDetailManager.save(avaliableConsumerDetail);
                    } else {
                        InvaliableConsumerDetail invaliableConsumerDetail = ObjectUtils.convertObj(companyDetail, InvaliableConsumerDetail.class);
                        invaliableConsumerDetailManager.save(invaliableConsumerDetail);
                    }
                    initialConsumerDetailManager.save(companyDetail);
                    Long resultMills = System.currentTimeMillis();
                    log.info("mark = " + mark + "耗时：" + (resultMills - startMills)/1000 + "秒");
                    i++;
                }
            }
            MailService.alreadNum.getAndIncrement();
        }
    }

    public static void main(String[]  args) {
        String hex = "3a";
        Integer id = Integer.parseUnsignedInt(hex, 16);
        System.out.println("this id is : " + id);
    }
}
