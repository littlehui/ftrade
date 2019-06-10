package com.littlehui.ftrade.mail.service;

/**
 * Created by littlehui on 2019/5/16.
 */

import com.littlehui.ftrade.dto.consumer.ConsumerDetailDTO;
import com.littlehui.ftrade.dto.params.ConsumerQueryParam;
import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.InvaliableConsumerDetail;
import com.littlehui.ftrade.mail.dao.AvaliableConsumerDetailManager;
import com.littlehui.ftrade.mail.dao.InvaliableConsumerDetailManager;
import com.u17173.treasurebox.utils.object.ObjectUtils;
import com.u17173.treasurebox.utils.query.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    AvaliableConsumerDetailManager avaliableConsumerDetailManager;

    @Autowired
    InvaliableConsumerDetailManager invaliableConsumerDetailManager;

    public Paged<ConsumerDetailDTO> pageAvaliableConsumerDetail(ConsumerQueryParam consumerQueryParam) {
        Paged<AvaliableConsumerDetail> avaliableConsumerDetailPaged = avaliableConsumerDetailManager.queryByPage(consumerQueryParam);
        List<ConsumerDetailDTO> consumerDetailDTOList = ObjectUtils.convertList(avaliableConsumerDetailPaged.getListData(), ConsumerDetailDTO.class);
        Paged<ConsumerDetailDTO> consumerDetailDTOPaged = new Paged<>(avaliableConsumerDetailPaged.getTotalCount()
                , avaliableConsumerDetailPaged.getPageNo()
                , avaliableConsumerDetailPaged.getPageSize()
                , consumerDetailDTOList);
        return consumerDetailDTOPaged;
    }

    public Paged<ConsumerDetailDTO> pageInvalibaleConsumerDetail(ConsumerQueryParam consumerQueryParam) {
        Paged<InvaliableConsumerDetail> invaliableConsumerDetailPaged = invaliableConsumerDetailManager.queryByPage(consumerQueryParam);
        List<ConsumerDetailDTO> consumerDetailDTOList = ObjectUtils.convertList(invaliableConsumerDetailPaged.getListData(), ConsumerDetailDTO.class);
        Paged<ConsumerDetailDTO> consumerDetailDTOPaged = new Paged<>(invaliableConsumerDetailPaged.getTotalCount()
                , invaliableConsumerDetailPaged.getPageNo()
                , invaliableConsumerDetailPaged.getPageSize()
                , consumerDetailDTOList);
        return consumerDetailDTOPaged;
    }
}
