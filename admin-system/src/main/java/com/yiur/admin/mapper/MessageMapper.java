package com.yiur.admin.mapper;

import com.yiur.admin.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息dao
 * @author Yiur
 */
@Mapper
@Repository
public interface MessageMapper {

    /**
     * 发送消息
     * @param message 消息属性
     * @return int
     */
    int sendMessage(Message message);

    /**
     * 删除消息
     * @param mid 消息id
     * @return int
     */
    int delMessage(@Param("mid") int mid);

    /**
     * 根据标题查询消息
     * @param title 标题
     * @return Message
     */
    Message queryByTitle(@Param("title") String title);

    /**
     * 根据身份查询消息
     * @param identity 身份编号
     * @return Message
     */
    Message queryByIdentity(@Param("identity") String identity);

}
