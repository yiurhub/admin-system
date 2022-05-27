package com.yiur.admin.controller;

import com.yiur.admin.pojo.ServiceHistory;
import com.yiur.admin.utils.HistoryUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * service操作历史
 * @author Yiur
 */
@RestController
@RequestMapping("/history/service")
public class ServiceHistoryController {

    /**
     * 历史记录工具类
     */
    @Autowired
    private HistoryUtil historyUtil;

    /**
     * 获取service层操作历史记录
     * @return List
     */
    @ApiOperation("获取service层操作历史记录")
    @GetMapping("/get/all")
    public List<ServiceHistory> getHistoryAll() {
        return historyUtil.getServiceHistory();
    }

}
