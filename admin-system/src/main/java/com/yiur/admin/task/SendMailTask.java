package com.yiur.admin.task;

import com.alibaba.fastjson.JSON;
import com.yiur.admin.AdminSystemApplication;
import com.yiur.admin.pojo.Mail;
import com.yiur.admin.pojo.Message;
import com.yiur.admin.pojo.User;
import com.yiur.admin.server.WebSocketServer;
import com.yiur.admin.service.MailService;
import com.yiur.admin.service.MessageService;
import com.yiur.admin.service.UserService;
import com.yiur.admin.state.Result;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * 发送消息任务
 * @author Yiur
 */
public class SendMailTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        UserService userService = AdminSystemApplication.context.getBean(UserService.class);
        MailService mailService = AdminSystemApplication.context.getBean(MailService.class);
        MessageService messageService = AdminSystemApplication.context.getBean(MessageService.class);
        Message message = JSON.parseObject(context.getMergedJobDataMap().get("message").toString(), Message.class);

        Map<String, WebSocketServer> webSocketMap = WebSocketServer.getWebSocketMap();

        Message currentMessage = messageService.queryByIdentity(message.getIdentity());
        List<User> users = userService.queryAll();
        for (User user : users) {
            int sendMail = mailService.sendMail(new Mail(user.getUid(), currentMessage.getMid()));
            if (webSocketMap.get(user.getUsername()) != null && sendMail == Result.OK.state) {
                webSocketMap.get(user.getUsername()).sendMessage("NEW_MAIL");
            }
        }
    }

}