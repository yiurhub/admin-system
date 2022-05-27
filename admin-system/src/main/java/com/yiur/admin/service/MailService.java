package com.yiur.admin.service;

import com.yiur.admin.pojo.Mail;

import java.util.List;

/**
 * 邮件service
 * @author Yiur
 */
public interface MailService {

    /**
     * 发送邮件
     * @param mail 邮件属性
     * @return int
     */
    int sendMail(Mail mail);

    /**
     * 查询全部邮件
     * @return List
     */
    List<Mail> queryAll();

    /**
     * 查询邮件
     * @param uid 用户id
     * @return List
     */
    List<Mail> queryById(int uid);

    /**
     * 修改邮件状态
     * @param mail 邮件属性
     * @return int
     */
    int updateState(Mail mail);

    /**
     * 删除邮件
     * @param mailId 邮件id
     * @return int
     */
    int delete(int mailId);

}
