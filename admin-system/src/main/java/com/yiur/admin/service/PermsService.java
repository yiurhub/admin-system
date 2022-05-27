package com.yiur.admin.service;

import com.yiur.admin.pojo.Perms;

import java.util.List;

/**
 * 权限service
 * @author Yiur
 */
public interface PermsService {

    /**
     * 查询全部权限
     * @return List
     */
    List<Perms> queryAll();

}
