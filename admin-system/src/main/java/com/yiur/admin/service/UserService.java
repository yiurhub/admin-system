package com.yiur.admin.service;

import com.yiur.admin.pojo.User;

import java.util.List;

/**
 * 用户service
 * @author Yiur
 */
public interface UserService {

    /**
     * 获取总数量
     * @param like 模糊查询
     * @return int
     */
    int getTotal(String like);

    /**
     * 添加用户
     * @param user 用户信息
     * @return int
     */
    int insert(User user);

    /**
     * 查询用户
     * @param user 用户信息
     * @return User
     */
    User query(User user);

    /**
     * 获取分页用户数据
     * @param index 当前页数
     * @param count 显示数量
     * @param like 模糊查询
     * @return list
     */
    List<User> queryByPage(int index, int count, String like);

    /**
     * 查询全部用户
     * @return List<User>
     */
    List<User> queryAll();

    /**
     * 删除用户
     * @param uid 用户id
     * @return int
     */
    int delete(int uid);

    /**
     * 修改用户
     * @param user 修改后的属性
     * @return int
     */
    int update(User user);

}
