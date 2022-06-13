package com.yiur.admin.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 邮件实体
 * @author Yiur
 */
@Data
@ApiModel("邮件实体")
@NoArgsConstructor
@AllArgsConstructor
public class Mail implements Serializable {

  private static final long serialVersionUID = 663870855817729973L;

  @ApiModelProperty("邮件id")
  private int mailId;
  @ApiModelProperty("邮件状态")
  private int state;

  @ApiModelProperty("用户对象")
  private User user;
  @ApiModelProperty("消息对象")
  private Message message;

  public Mail(int uid, int mid) {
    this.user = new User(uid);
    this.message = new Message(mid);
  }

}
