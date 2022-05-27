package com.yiur.admin.service.impl;

import com.yiur.admin.mapper.MessageMapper;
import com.yiur.admin.pojo.Message;
import com.yiur.admin.service.MessageService;
import org.func.spring.boot.annotation.FuncBean;
import org.func.spring.boot.annotation.FuncLambda;
import org.func.spring.boot.annotation.FuncParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息service
 * @author Yiur
 */
@Service
@FuncBean
@Transactional()
public class MessageServiceImpl implements MessageService {

    /**
     * 消息dao层
     */
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 发送消息
     *
     * @param message 消息属性
     * @return int
     */
    @Override
    @FuncLambda(classFile = MessageService.class, refs = { "service", "watch-log" })
    public int sendMessage(@FuncParameter("message") Message message) {
        return messageMapper.sendMessage(message);
    }

    /**
     * 删除消息
     *
     * @param mid 消息id
     * @return int
     */
    @Override
    @FuncLambda(classFile = MessageService.class, refs = { "service", "watch-log" })
    public int delMessage(@FuncParameter("mid") int mid) {
        return messageMapper.delMessage(mid);
    }

    /**
     * 根据标题查询消息
     *
     * @param title 标题
     * @return Message
     */
    @Override
    @FuncLambda(classFile = MessageService.class, refs = { "service", "watch-log" })
    public Message queryByTitle(@FuncParameter("title") String title) {
        return messageMapper.queryByTitle(title);
    }

    /**
     * 根据身份查询消息
     * @param identity 身份编号
     * @return Message
     */
    @Override
    @FuncLambda(classFile = MessageService.class, refs = { "service", "watch-log" })
    public Message queryByIdentity(@FuncParameter("identity") String identity) {
        return messageMapper.queryByIdentity(identity);
    }

}
