package com.littlehui.ftrade.mail.service;

import cn.hutool.core.util.ObjectUtil;
import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;
import com.littlehui.ftrade.mail.bean.InvaliableConsumerDetail;
import com.littlehui.ftrade.mail.dao.AvaliableConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.ConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InitialConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InvaliableConsumerDetailManager;
import com.u17173.treasurebox.utils.object.BeanUtils;
import com.u17173.treasurebox.utils.object.ClassUtil;
import com.u17173.treasurebox.utils.object.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public void validAllEmailBatch(String batch) {
        List<InitialConsumerDetail> initialConsumerDetails = initialConsumerDetailManager.findByBatch(batch);
        invaliableConsumerDetailManager.removeByBatch(batch);
        avaliableConsumerDetailManager.removeByBatch(batch);
        int markTotal = 5;
        for (int mark = 0; mark<markTotal; mark++) {
            MailCheckThread mailCheckThread = new MailCheckThread(this, initialConsumerDetails, mark);
            mailCheckThread.start();
        }
        while (true) {
            if (MailService.alreadNum.intValue() == markTotal) {
                break;
            }
        }
    }
    public class MailCheckThread extends Thread {

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
                    if (result) {
                        AvaliableConsumerDetail avaliableConsumerDetail = ObjectUtils.convertObj(companyDetail, AvaliableConsumerDetail.class);
                        avaliableConsumerDetailManager.save(avaliableConsumerDetail);
                    } else {
                        InvaliableConsumerDetail invaliableConsumerDetail = ObjectUtils.convertObj(companyDetail, InvaliableConsumerDetail.class);
                        invaliableConsumerDetailManager.save(invaliableConsumerDetail);
                    }
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
