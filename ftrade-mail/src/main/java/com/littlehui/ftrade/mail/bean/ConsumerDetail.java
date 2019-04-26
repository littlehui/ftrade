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
     * 1
     * 2
     * 3
     * 4
     * 5
     */
    private String batchInfo;
}
