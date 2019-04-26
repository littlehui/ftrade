package com.littlehui.ftrade.web.controller;

import com.littlehui.ftrade.dto.web.Response;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/test/{testInfo}")
    public Response<String> listIndexByUid(@PathVariable String testInfo) {
        return Response.getSuccessResponse(testInfo);
    }
}
