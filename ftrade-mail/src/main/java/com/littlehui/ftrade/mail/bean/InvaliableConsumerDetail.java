package com.littlehui.ftrade.mail.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description TODO
 * @ClassName InvaliableConsumerDetail
 * @Author littlehui
 * @Date 2019/4/25 18:04
 * @Version 1.0
 **/
@Document(collection = "InvaliableConsumerDetail")
@Data
public class InvaliableConsumerDetail extends ConsumerDetail {
}
