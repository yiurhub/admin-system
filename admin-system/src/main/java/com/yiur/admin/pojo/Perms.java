package com.yiur.admin.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限
 * @author Yiur
 */
@Data
@ApiModel("权限")
@NoArgsConstructor
@AllArgsConstructor
public class Perms implements Serializable {

    private static final long serialVersionUID = -1279364346004425782L;

    @ApiModelProperty("权限id")
    private int pid;
    @ApiModelProperty("权限")
    private String perm;

}
