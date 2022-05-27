package com.yiur.admin.service.impl;

import com.yiur.admin.mapper.MailMapper;
import com.yiur.admin.pojo.Mail;
import com.yiur.admin.service.MailService;
import org.func.spring.boot.annotation.FuncBean;
import org.func.spring.boot.annotation.FuncLambda;
import org.func.spring.boot.annotation.FuncParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邮件service
 * @author Yiur
 */
@Service
@FuncBean
@Transactional()
public class MailServiceImpl implements MailService {

    /**
     * 邮件dao层
     */
    @Autowired
    private MailMapper mailMapper;

    /**
     * 发送邮件
     *
     * @param mail 邮件属性
     * @return int
     */
    @Override
    @FuncLambda(classFile = MailService.class, refs = { "service", "watch-log" })
    public int sendMail(@FuncParameter("mail") Mail mail) {
        return mailMapper.sendMail(mail);
    }

    /**
     * 查询全部邮件
     *
     * @return List
     */
    @Override
    @FuncLambda(classFile = MailService.class, refs = { "service", "watch-log" })
    public List<Mail> queryAll() {
        return mailMapper.queryAll();
    }

    /**
     * 查询邮件
     *
     * @param uid 用户id
     * @return List
     */
    @Override
    @FuncLambda(classFile = MailService.class, refs = { "service", "watch-log" })
    public List<Mail> queryById(@FuncParameter("uid") int uid) {
        return mailMapper.queryById(uid);
    }

    /**
     * 修改邮件状态
     *
     * @param mail 邮件属性
     * @return int
     */
    @Override
    @FuncLambda(classFile = MailService.class, refs = { "service", "watch-log" })
    public int updateState(@FuncParameter("mail") Mail mail) {
        return mailMapper.updateState(mail);
    }

    /**
     * 删除邮件
     *
     * @param mailId 邮件id
     * @return int
     */
    @Override
    @FuncLambda(classFile = MailService.class, refs = { "service", "watch-log" })
    public int delete(@FuncParameter("mailId") int mailId) {
        return mailMapper.delete(mailId);
    }

}
