package com.littlehui.ftrade.tests.services;

import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;
import com.littlehui.ftrade.mail.service.CompanyDetailCreator;
import com.littlehui.ftrade.mail.service.MailImportService;
import com.littlehui.ftrade.web.FtradeWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @ClassName MailInportTest
 * @Author littlehui
 * @Date 2019/4/25 10:58
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FtradeWebApplication.class)
@ActiveProfiles("local")
public class MailImportTest {

    @Autowired
    MailImportService mailImportService;

    @Test
    public void testImportMails() {
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届五金类.xlsx"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[2]);
                        companyDetail.setMail(excelRow[4]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[3]);
                        companyDetail.setCountryOrZone(excelRow[0]);
                        companyDetail.setNeedProduct(excelRow[1]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("61");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailJiaJu() {
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届家居装饰.xlsx"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[8]);
                        companyDetail.setPhone(excelRow[6]);
                        companyDetail.setFax(excelRow[7]);
                        companyDetail.setCompanyAddress(excelRow[4]);
                        companyDetail.setMan(excelRow[3]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[9]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("62");
                        return companyDetail;
                    }
                }, 10);
    }

    @Test
    public void testImportMailJiDian() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届家用电器.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[1]);
                        companyDetail.setPhone(excelRow[2]);
                        companyDetail.setFax(excelRow[3]);
                        companyDetail.setCompanyAddress(excelRow[4]);
                        companyDetail.setMan(excelRow[5]);
                        companyDetail.setCountryOrZone(excelRow[6]);
                        companyDetail.setNeedProduct(excelRow[7]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("63");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailJianCai() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届建材类.xlsx"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[2]);
                        companyDetail.setMail(excelRow[4]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[3]);
                        companyDetail.setCountryOrZone(excelRow[0]);
                        companyDetail.setNeedProduct(excelRow[1]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("64");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailZhaoMing() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届照明类.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[8]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[5]);
                        companyDetail.setCountryOrZone(excelRow[0]);
                        companyDetail.setNeedProduct(excelRow[2] + "_" + excelRow[3]);
                        try {
                            //companyDetail.setWebSite(excelRow[8]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("65");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailDianziXiaofeiPin() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届电子消费品及信息产品.xlsx"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[1]);
                        companyDetail.setPhone(excelRow[2]);
                        companyDetail.setFax(excelRow[3]);
                        companyDetail.setCompanyAddress(excelRow[4]);
                        companyDetail.setMan(excelRow[5]);
                        companyDetail.setCountryOrZone(excelRow[6]);
                        companyDetail.setNeedProduct(excelRow[7]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("66");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailDianZiDianQi() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/121届电子电气.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[1]);
                        companyDetail.setPhone(excelRow[2]);
                        companyDetail.setFax(excelRow[3]);
                        companyDetail.setCompanyAddress(excelRow[4]);
                        companyDetail.setMan(excelRow[5]);
                        companyDetail.setCountryOrZone(excelRow[6]);
                        companyDetail.setNeedProduct(excelRow[7]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("67");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailWujin() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/五金.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[3]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("68");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailWeiYu() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/卫浴.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("69");
                        return companyDetail;
                    }
                }, 11);
    }


    @Test
    public void testImportMailJiaJu1() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/家具.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("70");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailJiaYongDianQi() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/家用电器.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[5]);
                        companyDetail.setPhone(excelRow[1]);
                        companyDetail.setFax(excelRow[2]);
                        companyDetail.setCompanyAddress(excelRow[3]);
                        companyDetail.setMan(excelRow[0]);
                        companyDetail.setCountryOrZone("");
                        companyDetail.setNeedProduct("家用电器");
                        companyDetail.setWebSite(excelRow[6]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("71");
                        return companyDetail;
                    }
                }, 7);
    }

    @Test
    public void testImportMailXiaoXingJiXie() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/小型机械.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("72");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailGongJu() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/工具.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("73");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailJianZhuZhuangShi() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/建筑及装饰材料.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("74");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailDianZiXiaoFei() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/电子消费品.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("75");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailCaiGouShang() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/电子采购商名录.xlsx"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[1]);
                        companyDetail.setPhone(excelRow[2]);
                        companyDetail.setFax(excelRow[3]);
                        companyDetail.setCompanyAddress(excelRow[4]);
                        companyDetail.setMan(excelRow[5]);
                        companyDetail.setCountryOrZone(excelRow[6]);
                        companyDetail.setNeedProduct(excelRow[7]);
                        companyDetail.setWebSite(excelRow[8]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("76");
                        return companyDetail;
                    }
                }, 9);
    }

    @Test
    public void testImportMailLp() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/礼品及赠品.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("77");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailJieRi() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/节日用品.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("78");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailTaoCi() {
        //无法读取
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/shaixuanguo/陶瓷.xls"
                , new CompanyDetailCreator() {
                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(excelRow[9]);
                        companyDetail.setPhone(excelRow[5]);
                        companyDetail.setFax(excelRow[6]);
                        companyDetail.setCompanyAddress(excelRow[7]);
                        companyDetail.setMan(excelRow[4]);
                        companyDetail.setCountryOrZone(excelRow[1]);
                        companyDetail.setNeedProduct(excelRow[2]);
                        companyDetail.setWebSite(excelRow[10]);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("79");
                        return companyDetail;
                    }
                }, 11);
    }

    @Test
    public void testImportMailNew8X() {
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/202005/email5.xlsx"
                , new CompanyDetailCreator() {

                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        String UNKNOWN = "UNKNOWN";
                        String mail = excelRow[0];
                        mail = mail.replaceAll(";","");
                        mail = mail.replaceAll(",", "");
                        String[] mailSprlitArrays = mail.split("@");
                        String man = mailSprlitArrays[0];
                        companyDetail.setCompanyName(UNKNOWN);
                        companyDetail.setMail(mail);
                        companyDetail.setPhone(UNKNOWN);
                        companyDetail.setFax(UNKNOWN);
                        companyDetail.setCompanyAddress(UNKNOWN);
                        companyDetail.setMan(man);
                        companyDetail.setCountryOrZone(UNKNOWN);
                        companyDetail.setNeedProduct(UNKNOWN);
                        companyDetail.setWebSite(UNKNOWN);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("83");
                        return companyDetail;
                    }

                }, 1);
    }

    @Test
    public void testImportMailNew84() {
        mailImportService.importCompanyDetailFromXls("/Users/littlehui/WorkSpaces/Home/ftrade/docs/maillist/202005/email4.xlsx"
                , new CompanyDetailCreator() {

                    @Override
                    public InitialConsumerDetail createInitialConsumerDetail(String[] excelRow) {
                        InitialConsumerDetail companyDetail = new InitialConsumerDetail();
                        String UNKNOWN = "UNKNOWN";
                        String mail = excelRow[1];
                        mail = mail.replaceAll(";","");
                        mail = mail.replaceAll(",", "");
                        String[] mailSprlitArrays = mail.split("@");
                        String man = mailSprlitArrays[0];
                        companyDetail.setCompanyName(excelRow[0]);
                        companyDetail.setMail(mail);
                        companyDetail.setPhone(excelRow[2]);
                        companyDetail.setFax(excelRow[2]);
                        companyDetail.setCompanyAddress(excelRow[5]);
                        companyDetail.setMan(man);
                        companyDetail.setCountryOrZone(excelRow[5]);
                        companyDetail.setNeedProduct(excelRow[6]);
                        companyDetail.setWebSite(UNKNOWN);
                        companyDetail.setSendCount(0);
                        companyDetail.setBatchInfo("84");
                        return companyDetail;
                    }

                }, 7);
    }
}
