package com.yiur.admin.service.impl;

import com.yiur.admin.service.SchedulerService;
import com.yiur.admin.utils.TaskUtil;
import org.func.spring.boot.annotation.FuncBean;
import org.func.spring.boot.annotation.FuncLambda;
import org.func.spring.boot.annotation.FuncParameter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yiur
 */
@Service
@FuncBean
public class SchedulerServiceImpl implements SchedulerService {

    /**
     * 定时任务
     */
    @Autowired
    private Scheduler scheduler;

    @Override
    @FuncLambda(classFile = SchedulerService.class, refs = { "service", "watch-log" })
    public boolean add(@FuncParameter("jobDetail") JobDetail jobDetail, @FuncParameter("trigger") Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @FuncLambda(classFile = SchedulerService.class, refs = { "service", "watch-log" })
    public boolean update(@FuncParameter("name") String name, @FuncParameter("group") String group, @FuncParameter("cron") String cron) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            if (!CronExpression.isValidExpression(cron) || !scheduler.checkExists(jobKey)) {
                return false;
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            Trigger trigger = TaskUtil.trigger(name, group, cron);
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @FuncLambda(classFile = SchedulerService.class, refs = { "service", "watch-log" })
    public boolean delete(@FuncParameter("name") String name, @FuncParameter("group") String group) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            if (!scheduler.checkExists(jobKey)) {
                return false;
            }
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @FuncLambda(classFile = SchedulerService.class, refs = { "service", "watch-log" })
    public boolean pause(@FuncParameter("name") String name, @FuncParameter("group") String group) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            if (!scheduler.checkExists(jobKey)) {
                return false;
            }
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @FuncLambda(classFile = SchedulerService.class, refs = { "service", "watch-log" })
    public boolean resume(@FuncParameter("name") String name, @FuncParameter("group") String group) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            if (!scheduler.checkExists(jobKey)) {
                return false;
            }
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
