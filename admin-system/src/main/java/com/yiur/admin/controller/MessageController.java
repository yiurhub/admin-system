package com.yiur.admin.controller;

import com.alibaba.fastjson.JSON;
import com.yiur.admin.service.SchedulerService;
import com.yiur.admin.task.SendMailTask;
import com.yiur.admin.pojo.Message;
import com.yiur.admin.service.MessageService;
import com.yiur.admin.state.Result;
import com.yiur.admin.task.ValidMessageTask;
import com.yiur.admin.utils.CornUtil;
import com.yiur.admin.utils.TaskUtil;
import io.swagger.annotations.ApiOperation;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 消息管理
 * @author Yiur
 */
@RestController
@RequestMapping("/msg")
public class MessageController {

    /**
     * 消息service层
     */
    @Autowired
    private MessageService messageService;
    /**
     * 定时任务
     */
    @Autowired
    private SchedulerService schedulerService;

    /**
     * 发送消息
     * @param message 用户属性
     * @return int
     */
    @ApiOperation("发送消息")
    @PostMapping("/send")
    public int sendMessage(@RequestBody Message message) {
        message.setIdentity(UUID.randomUUID().toString());
        if (messageService.sendMessage(message) == 1) {
            try {
                sendMail(message);
                setValid(message);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return Result.OK.state;
        }
        return Result.ERROR.state;
    }

    /**
     * 发送邮件
     * @param message 消息属性
     * @throws SchedulerException 异常
     */
    private void sendMail(Message message) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", JSON.toJSON(message));

        String corn = CornUtil.corn(message.getSendDate());
        schedulerService.add(TaskUtil.jobDetail(SendMailTask.class, "message",  jobDataMap), TaskUtil.trigger(SendMailTask.class, "message", corn));
    }

    /**
     * 设置有效时间
     * @param message 消息属性
     * @throws SchedulerException 异常
     */
    private void setValid(Message message) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", JSON.toJSON(message));

        String corn = CornUtil.corn(message.getValidDate());
        schedulerService.add(TaskUtil.jobDetail(ValidMessageTask.class, "message", jobDataMap), TaskUtil.trigger(ValidMessageTask.class, "message", corn));
    }

}
