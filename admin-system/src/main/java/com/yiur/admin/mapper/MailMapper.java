package com.yiur.admin.mapper;

import com.yiur.admin.pojo.Mail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 邮件
 * @author Yiur
 */
@Mapper
@Repository
public interface MailMapper {

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
    List<Mail> queryById(@Param("uid") int uid);

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
    int delete(@Param("mailId") int mailId);

}
