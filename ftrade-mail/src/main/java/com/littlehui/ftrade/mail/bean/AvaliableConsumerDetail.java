package com.littlehui.ftrade.mail.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description TODO
 * @ClassName AvaliableConsumerDetail
 * @Author littlehui
 * @Date 2019/4/25 18:04
 * @Version 1.0
 **/
@Document(collection = "AvaliableConsumerDetail")
@Data
public class AvaliableConsumerDetail extends ConsumerDetail {
}
