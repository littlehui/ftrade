package com.littlehui.ftrade.mail.bean;

import com.u17173.treasurebox.manager.bean.MongoBaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * Created by littlehui on 2019/4/3.
 */
@Document(collection = "ConsumerDetail")
@Data
public class ConsumerDetail extends MongoBaseEntity {

    @Id
    private String id;

    private String companyName;

    private String mail;

    private String phone;

    private String fax;

    private String companyAddress;

    private String man;

    private String countryOrZone;

    private String needProduct;

    private String webSite;

    private Integer sendCount;

    private Date sendTime;

    private Boolean available;

    /**
     * 1   询盘1
     * 2   询盘2
     * 3   询盘3
     * 4   离线邮件提取的邮箱
     * 5
     * 6X  展会Excel导入的
     * 7X  展会Excel导入的
     * 8X  zlj哪里弄来的
     */
    private String batchInfo;

    private Boolean testedFlag;

    private Boolean sendingFlag;
}
