package com.yiur.admin.service;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 定时任务
 * @author Yiur
 */
public interface SchedulerService {

    /**
     * 添加
     * @return boolean
     */
    boolean add(JobDetail jobDetail, Trigger trigger);

    /**
     * 更新
     * @return boolean
     */
    boolean update(String name, String group, String cron);

    /**
     * 删除
     * @return boolean
     */
    boolean delete(String name, String group);

    /**
     * 恢复
     * @return boolean
     */
    boolean pause(String name, String group);

    /**
     * 暂停
     * @return boolean
     */
    boolean resume(String name, String group);

}
