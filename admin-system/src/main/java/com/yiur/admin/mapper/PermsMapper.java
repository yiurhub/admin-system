package com.yiur.admin.mapper;

import com.yiur.admin.pojo.Perms;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限
 * @author Yiur
 */
@Mapper
@Repository
public interface PermsMapper {

    /**
     * 查询全部权限
     * @return List
     */
    List<Perms> queryAll();

}
