package com.littlehui.ftrade.mail.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by littlehui on 2019/4/3.
 */
@Document(collection = "InitialConsumerDetail")
@Data
public class InitialConsumerDetail extends ConsumerDetail {

}
