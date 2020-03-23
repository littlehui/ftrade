package com.littlehui.ftrade.dto.consumer;

import lombok.Data;

import java.util.Date;

/**
 * Created by littlehui on 2019/5/16.
 */
@Data
public class ConsumerDetailDTO {

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

    private Boolean testedFlag;
}
