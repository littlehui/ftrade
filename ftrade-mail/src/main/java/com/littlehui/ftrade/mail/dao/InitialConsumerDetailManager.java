package com.littlehui.ftrade.mail.dao;

import com.littlehui.ftrade.mail.bean.InitialConsumerDetail;
import com.u17173.treasurebox.auth.mongo.dao.AbstractManager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @ClassName InitialConsumerDetailManager
 * @Author littlehui
 * @Date 2019/4/25 11:20
 * @Version 1.0
 **/
@Repository
public class InitialConsumerDetailManager extends AbstractManager<InitialConsumerDetail, String> {

    public InitialConsumerDetailManager() {
    }

    public List<InitialConsumerDetail> findByBatch(String batch) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("batchInfo").is(batch);
        query.addCriteria(criteria);
        return this.findList(query);
    }

    public List<InitialConsumerDetail> findByBatchAndNonTested(String batch) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("batchInfo").is(batch).orOperator(new Criteria().and("testedFlag").exists(false), new Criteria().and("testedFlag").is(false));
        query.addCriteria(criteria);
        List<InitialConsumerDetail> initialConsumerDetails = this.findList(query);
        return initialConsumerDetails;
    }
}
