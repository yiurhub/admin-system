package com.yiur.admin.utils;

import org.quartz.*;

import java.util.UUID;

/**
 * 任务调度工具类
 * @author Yiur
 */
public final class TaskUtil {

    /**
     * 创建定时器
     * @param execute 执行触发器的类
     * @param corn 任务调度的corn表达式
     * @return Trigger
     */
    public static Trigger trigger(Class<?> execute, String corn) {
        return trigger(execute.getName() + UUID.randomUUID(), null, corn);
    }

    /**
     * 创建定时器
     * @param execute 执行触发器的类
     * @param group job 组
     * @param corn 任务调度的corn表达式
     * @return Trigger
     */
    public static Trigger trigger(Class<?> execute, String group, String corn) {
        return trigger(execute.getName() + UUID.randomUUID(), group, corn);
    }

    /**
     * 创建定时器
     * @param name job 名称
     * @param group job 组
     * @param corn 任务调度的corn表达式
     * @return Trigger
     */
    public static Trigger trigger(String name, String group, String corn) {
        return TriggerBuilder.newTrigger()
                .withIdentity(name, group)
                .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                .startNow()
                .build();
    }

    /**
     * 创建执行任务对象
     * @param job 执行任务对象
     * @param jobDataMap 执行任务的属性
     * @return JobDetail
     */
    public static JobDetail jobDetail(Class<? extends Job> job, JobDataMap jobDataMap) {
        return jobDetail(job, job.getName() + UUID.randomUUID(), null, jobDataMap);
    }

    /**
     * 创建执行任务对象
     * @param job 执行任务对象
     * @param group job 组
     * @param jobDataMap 执行任务的属性
     * @return JobDetail
     */
    public static JobDetail jobDetail(Class<? extends Job> job, String group, JobDataMap jobDataMap) {
        return jobDetail(job, job.getName() + UUID.randomUUID(), group, jobDataMap, false);
    }

    /**
     * 创建执行任务对象
     * @param job 执行任务对象
     * @param name job 名称
     * @param group job 组
     * @param jobDataMap 执行任务的属性
     * @return JobDetail
     */
    public static JobDetail jobDetail(Class<? extends Job> job, String name, String group, JobDataMap jobDataMap) {
        return jobDetail(job, name, group, jobDataMap, false);
    }

    /**
     * 创建执行任务对象
     * @param job 执行任务对象
     * @param name job 名称
     * @param group job 组
     * @param jobDataMap 执行任务的属性
     * @param storeDurably 持久存储
     * @return JobDetail
     */
    public static JobDetail jobDetail(Class<? extends Job> job, String name, String group, JobDataMap jobDataMap, boolean storeDurably) {
        return JobBuilder.newJob(job)
                .withIdentity(name, group)
                .usingJobData(jobDataMap)
                .storeDurably(storeDurably)
                .build();
    }

}
