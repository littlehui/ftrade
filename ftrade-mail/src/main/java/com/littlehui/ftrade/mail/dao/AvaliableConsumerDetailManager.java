package com.littlehui.ftrade.mail.dao;

import com.littlehui.ftrade.mail.bean.AvaliableConsumerDetail;
import com.littlehui.ftrade.mail.bean.ConsumerDetail;
import com.u17173.treasurebox.auth.mongo.dao.AbstractManager;
import com.u17173.treasurebox.utils.query.Paged;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @ClassName AvaliableConsumerDetailManager
 * @Author littlehui
 * @Date 2019/4/25 18:03
 * @Version 1.0
 **/
@Repository
public class AvaliableConsumerDetailManager extends AbstractManager<AvaliableConsumerDetail, String> {

    public AvaliableConsumerDetailManager() {

    }

    public void removeByBatch(String batch) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("batchInfo").is(batch);
        query.addCriteria(criteria);
        this.remove(query);
    }

    public Paged<AvaliableConsumerDetail> queryConsumerDetails(int pageNo, Integer pageSize, Integer limitCount) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("sendCount").lt(limitCount);
        query.addCriteria(criteria);
        return this.findPage(query, pageNo, pageSize);
    }

    public Paged<AvaliableConsumerDetail> queryConsumerDetailsBatch(int pageNo, Integer pageSize, Integer limitCount, String batch) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("sendCount").lt(limitCount).and("batchInfo").is(batch);
        query.addCriteria(criteria);
        return this.findPage(query, pageNo, pageSize);    }
}
