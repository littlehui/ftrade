package com.littlehui.ftrade.mail.dao;

import com.littlehui.ftrade.mail.bean.ConsumerDetail;
import com.u17173.treasurebox.auth.mongo.dao.AbstractManager;
import com.u17173.treasurebox.utils.query.Paged;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by littlehui on 2019/4/3.
 */
@Repository
public class ConsumerDetailManager extends AbstractManager<ConsumerDetail, String> {

    public ConsumerDetailManager() {
    }

    public boolean hasMail(String mail) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("mail").is(mail);
        query.addCriteria(criteria);
        return this.exist(query);
    }

    public Paged<ConsumerDetail> queryConsumerDetails(int pageNo, Integer pageSize, Integer limitCount) {
        Query query = new Query();
        Criteria criteria = new Criteria().and("sendCount").lt(limitCount);
        query.addCriteria(criteria);
        return this.findPage(query, pageNo, pageSize);
    }
}
