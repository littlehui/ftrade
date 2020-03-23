package com.littlehui.ftrade.mail.dao;

import com.littlehui.ftrade.dto.params.ConsumerQueryParam;
import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.InvaliableConsumerDetail;
import com.u17173.treasurebox.auth.mongo.dao.AbstractManager;
import com.u17173.treasurebox.utils.query.Paged;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @ClassName InvaliableConsumerDetailManager
 * @Author littlehui
 * @Date 2019/4/25 18:03
 * @Version 1.0
 **/
@Repository
public class InvaliableConsumerDetailManager extends AbstractManager<InvaliableConsumerDetail, String> {

    public InvaliableConsumerDetailManager() {

    }

    public void removeByBatch(String batch) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("batchInfo").is(batch);
        query.addCriteria(criteria);
        this.remove(query);
    }

    public void removeByIds(String[] ids) {
        this.remove(ids);
    }

    public Paged<InvaliableConsumerDetail> queryByPage(ConsumerQueryParam consumerQueryParam) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (consumerQueryParam.getSendCount() != null) {
            criteria.and("sendCount").is(consumerQueryParam.getSendCount());
        }
        query.addCriteria(criteria);
        Paged<InvaliableConsumerDetail> avaliableConsumerDetailPaged = this.findPage(query, consumerQueryParam.getPageNo(), consumerQueryParam.getPageSize());
        return avaliableConsumerDetailPaged;
    }
}
