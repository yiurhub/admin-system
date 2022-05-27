package com.yiur.admin.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页对象
 * @author Yiur
 */
@Data
@ApiModel("分页")
@NoArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = 5523949922193211624L;

    @ApiModelProperty("当前页")
    private int pageIndex;
    @ApiModelProperty("显示数量")
    private int pageCount;
    @ApiModelProperty("页总数")
    private int pageTotal;
    @ApiModelProperty("模糊查询")
    private String pageLike;

    public Page(int pageCount, int pageTotal) {
        this.pageCount = pageCount;
        this.pageTotal = pageTotal;
    }

    public Page(int pageCount, int pageTotal, String pageLike) {
        this.pageCount = pageCount;
        this.pageTotal = pageTotal;
        this.pageLike = pageLike;
    }

}
