package com.cao.service;

import com.cao.dao.QuartzDao;
import com.cao.model.QuartzModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时器设置
 */
@Service
public class QuartzService {

    @Autowired
    private QuartzDao quartzDao;

    /**
     * 根据ID查询数据
     * @return
     */
    public QuartzModel queryRecord() throws Exception {
        return this.quartzDao.queryRecord();
    }

    /**
     * 更新一条记录
     * @param quartzModel
     * @throws Exception
     */
    public void updateOne(QuartzModel quartzModel) throws Exception{
        this.quartzDao.updateOne(quartzModel);
    }
}
