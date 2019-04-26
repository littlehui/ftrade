package com.littlehui.ftrade.dto.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * @Description TODO
 * @ClassName ConsumerDetail
 * @Author littlehui
 * @Date 2019/4/9 18:20
 * @Version 1.0
 **/
@Document(collection = "ConsumerDetail")
@Data
public class ConsumerDetail {
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
