package com.littlehui.ftrade.web.controller;

import com.littlehui.ftrade.dto.consumer.ConsumerDetailDTO;
import com.littlehui.ftrade.dto.params.ConsumerQueryParam;
import com.littlehui.ftrade.dto.web.Response;
import com.littlehui.ftrade.mail.service.ConsumerService;
import com.u17173.treasurebox.utils.query.Paged;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @ClassName ConsumerController
 * @Author littlehui
 * @Date 2019/4/2 15:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/consumer")
@Slf4j
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/test/{testInfo}")
    public Response<String> listIndexByUid(@PathVariable String testInfo) {
        return Response.getSuccessResponse(testInfo);
    }

    @GetMapping("/avaliable/list")
    public Response<Paged<ConsumerDetailDTO>> avaliableList(ConsumerQueryParam queryParam) {
        return Response.getSuccessResponse(consumerService.pageAvaliableConsumerDetail(queryParam));
    }

    @GetMapping("/invaliable/list")
    public Response<Paged<ConsumerDetailDTO>> invaliableList(ConsumerQueryParam queryParam) {
        return Response.getSuccessResponse(consumerService.pageInvalibaleConsumerDetail(queryParam));
    }
}
