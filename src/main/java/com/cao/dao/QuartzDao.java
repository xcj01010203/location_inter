package com.cao.dao;

import com.cao.common.BaseDao;
import com.cao.model.QuartzModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时器设置
 */
@Repository
public class QuartzDao extends BaseDao<QuartzModel> {

    /**
     * 根据ID查询数据
     * @return
     */
    public QuartzModel queryRecord() throws Exception {
        String sql = "select * from "+ QuartzModel.TABLE_NAME;
        return this.queryForObject(sql, null, QuartzModel.class);
    }
}
