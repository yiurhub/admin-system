package com.yiur.admin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息实体
 * @author Yiur
 */
@Data
@ApiModel("消息实体")
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 5743158056212225588L;

    @ApiModelProperty("消息id")
    private int mid;
    @ApiModelProperty("身份")
    private String identity;
    @ApiModelProperty("消息等级")
    private int level;
    @ApiModelProperty("消息目标")
    private String target;
    @ApiModelProperty("消息标题")
    private String title;
    @ApiModelProperty("消息内容")
    private String content;
    @ApiModelProperty("发送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendDate;
    @ApiModelProperty("有效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate;

    public Message(int mid) {
        this.mid = mid;
    }

}
