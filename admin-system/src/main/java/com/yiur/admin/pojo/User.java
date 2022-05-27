package com.yiur.admin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author Yiur
 */
@Data
@ApiModel("用户实体")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 750215562128328810L;

    @ApiModelProperty("用户在线状态")
    private boolean online;

    @ApiModelProperty("用户id")
    private int uid;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("用户上一次登陆地址")
    private String lastLoginAddress;
    @ApiModelProperty("用户头像")
    private String face;
    @ApiModelProperty("用户简介")
    private String desc;
    @ApiModelProperty("用户上一次登陆时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginDate;
    @ApiModelProperty("用户注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerDate;
    @ApiModelProperty("用户权限")
    private String perms;
    @ApiModelProperty("是否删除")
    private boolean deleted;

    public User(int uid) {
        this.uid = uid;
    }

    public User(int uid, String perms) {
        this.uid = uid;
        this.perms = perms;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int uid, Date lastLoginDate) {
        this.uid = uid;
        this.lastLoginDate = lastLoginDate;
    }

    public User(int uid, String lastLoginAddress, Date lastLoginDate) {
        this.uid = uid;
        this.lastLoginAddress = lastLoginAddress;
        this.lastLoginDate = lastLoginDate;
    }

    public User(String username, String password, String name, String face) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.face = face;
    }

}
