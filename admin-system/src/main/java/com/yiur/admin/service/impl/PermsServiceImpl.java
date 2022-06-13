package com.yiur.admin.service.impl;

import com.yiur.admin.mapper.PermsMapper;
import com.yiur.admin.pojo.Perms;
import com.yiur.admin.service.PermsService;
import org.func.spring.boot.annotation.FuncBean;
import org.func.spring.boot.annotation.FuncLambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限service
 *
 * @author Yiur
 */
@Service
@FuncBean
@Transactional(rollbackFor = { Exception.class })
public class PermsServiceImpl implements PermsService {

    /**
     * 权限dao层
     */
    @Autowired
    private PermsMapper permsMapper;

    /**
     * 查询全部权限
     *
     * @return List
     */
    @Override
    @FuncLambda(classFile = PermsService.class, refs = { "service", "watch-log" })
    public List<Perms> queryAll() {
        return permsMapper.queryAll();
    }

}
