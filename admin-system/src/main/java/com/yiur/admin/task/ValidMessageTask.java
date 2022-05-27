package com.yiur.admin.task;

import com.alibaba.fastjson.JSON;
import com.yiur.admin.AdminSystemApplication;
import com.yiur.admin.pojo.Message;
import com.yiur.admin.service.MessageService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 有效消息任务
 * @author Yiur
 */
public class ValidMessageTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        MessageService messageService = AdminSystemApplication.context.getBean(MessageService.class);
        Message message = JSON.parseObject(context.getMergedJobDataMap().get("message").toString(), Message.class);

        Message currentMessage = messageService.queryByIdentity(message.getIdentity());
        if (currentMessage != null) {
            messageService.delMessage(currentMessage.getMid());
        }
    }

}