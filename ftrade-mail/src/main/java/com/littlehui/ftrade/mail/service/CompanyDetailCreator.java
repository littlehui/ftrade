package com.littlehui.ftrade.mail.service;

import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;

/**
 * @Description TODO
 * @ClassName CompayDetailCreator
 * @Author littlehui
 * @Date 2019/4/25 11:31
 * @Version 1.0
 **/
public interface CompanyDetailCreator {

    public InitialConsumerDetail createInitialConsumerDetail(String[] rows);
}
