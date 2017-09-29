package com.cao.dao;

import com.cao.common.BaseDao;
import com.cao.common.Page;
import com.cao.model.LocationModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 位置信息
 */
@Repository
public class LocationDao extends BaseDao<LocationModel> {

    /**
     * 获取所有记录
     * @return
     */
    public Page querylocationlist(Page page) {
        String sql = " select * from t_location order by responseTimestampEpoch desc ";

        return this.query(sql, null, page, LocationModel.class);
    }
}
