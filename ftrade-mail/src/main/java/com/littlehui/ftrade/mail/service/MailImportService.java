package com.littlehui.ftrade.mail.service;

import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.ConsumerDetail;
import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;
import com.littlehui.ftrade.mail.dao.AvaliableConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.ConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InitialConsumerDetailManager;
import com.u17173.treasurebox.utils.excels.ExcelResult;
import com.u17173.treasurebox.utils.excels.ExcelUtil;
import com.u17173.treasurebox.utils.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @ClassName MailImportService
 * @Author littlehui
 * @Date 2019/4/25 10:59
 * @Version 1.0
 **/
@Service
public class MailImportService {

    @Autowired
    InitialConsumerDetailManager initialConsumerDetailManager;

    @Autowired
    ConsumerDetailManager consumerDetailManager;

    @Autowired
    AvaliableConsumerDetailManager avaliableConsumerDetailManager;

    public void consumerDetailToAvaliableComsumerDetail() {
        Query query = new Query();
        query.addCriteria(new Criteria().and("sendCount").is(1));
        List<ConsumerDetail> consumerDetailList = consumerDetailManager.findList(query);
        consumerDetailList.forEach(f -> f.setId(null));
        List<AvaliableConsumerDetail> avaliableConsumerDetails = ObjectUtils.convertList(consumerDetailList, AvaliableConsumerDetail.class);
        avaliableConsumerDetailManager.saveCollection(avaliableConsumerDetails);
    }

    public void importCompanyDetailFromXls(String filePath, int cellPerRow) {
        ExcelResult excelResult = ExcelUtil.readExcelByPath(filePath, cellPerRow, 2);
        List<InitialConsumerDetail> companyDetailList = new ArrayList<InitialConsumerDetail>();
        for (String[] row : excelResult) {
            InitialConsumerDetail companyDetail = newCompanyDetail(row);
            companyDetailList.add(companyDetail);
            initialConsumerDetailManager.save(companyDetail);
        }
    }

    public void importCompanyDetailFromXls(String filePath, CompanyDetailCreator companyDetailCreator, int cellPerRow) {
        ExcelResult excelResult = ExcelUtil.readExcelByPath(filePath, cellPerRow, 2);
        List<InitialConsumerDetail> companyDetailList = new ArrayList<InitialConsumerDetail>();
        for (String[] row : excelResult) {
            InitialConsumerDetail companyDetail = companyDetailCreator.createInitialConsumerDetail(row);
            companyDetailList.add(companyDetail);
            initialConsumerDetailManager.save(companyDetail);
        }
    }

    private InitialConsumerDetail newCompanyDetail(String[] excelRow) {
        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
        companyDetail.setCompanyName(excelRow[2]);
        companyDetail.setMail(caculateRealMail(excelRow[4]));
        companyDetail.setPhone(excelRow[5]);
        companyDetail.setFax(excelRow[6]);
        companyDetail.setCompanyAddress(excelRow[7]);
        companyDetail.setMan(excelRow[3]);
        companyDetail.setCountryOrZone(excelRow[0]);
        companyDetail.setNeedProduct(excelRow[1]);
        companyDetail.setWebSite(excelRow[8]);
        companyDetail.setSendCount(0);
        return companyDetail;
    }

    private String caculateRealMail(String tempMail) {
        tempMail = tempMail.trim();
        String realMail = tempMail;
        if (validMail(tempMail)) {
            realMail = tempMail;
        } else {
            String regex=".*<(.*?)>";
            Pattern mailPattern =  Pattern.compile(regex);
            Matcher matcher = mailPattern.matcher(tempMail);
            if (matcher.matches() == true) {
                realMail = matcher.group(1);
            }
        }
        return realMail;
    }

    private Boolean validMail(String tempMail) {
        String regex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        //String regex=".*<(.*?)>";
        Pattern mailPattern =  Pattern.compile(regex);
        Matcher matcher = mailPattern.matcher(tempMail);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String tmpMail = "ana<ana@globebazarhouse.com> ";
        String regex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        //String regex=".*<(.*?)>";
        Pattern mailPattern =  Pattern.compile(regex);
        Matcher matcher = mailPattern.matcher(tmpMail);
        System.out.print(matcher);
    }
}
