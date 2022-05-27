package com.yiur.admin.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Yiur
 */
@Data
@ApiModel("service历史记录")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceHistory implements Serializable {

    private static final long serialVersionUID = 149231074480108807L;

    private String info;
    private String result;

}
