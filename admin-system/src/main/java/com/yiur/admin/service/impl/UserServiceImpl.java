package com.yiur.admin.service.impl;

import com.yiur.admin.mapper.UserMapper;
import com.yiur.admin.pojo.User;
import com.yiur.admin.service.UserService;
import org.func.spring.boot.annotation.FuncBean;
import org.func.spring.boot.annotation.FuncLambda;
import org.func.spring.boot.annotation.FuncParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.func.spring.boot.utils.StringUtil.format;

/**
 * 用户service
 * @author Yiur
 */
@Service
@FuncBean
@Transactional()
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    /**
     * 用户dao层
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取总数量
     *
     * @param like 模糊查询
     * @return int
     */
    @Override
    @Cacheable(key = "'user_cache_total_' + ((#like == null || #like.equals('')) ? 'all' : #like)")
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public int getTotal(@FuncParameter("like") String like) {
        return userMapper.getTotal("".equals(like) ? null : format("%?%", like));
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return int
     */
    @Override
    @CacheEvict(cacheNames = "user", allEntries = true)
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public int insert(@FuncParameter("user") User user) {
        return userMapper.insert(user);
    }

    /**
     * 查询用户
     *
     * @param user 用户信息
     * @return User
     */
    @Override
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public User query(@FuncParameter("user") User user) {
        return userMapper.query(user);
    }

    /**
     * 获取分页用户数据
     *
     * @param index 当前页数
     * @param count 显示数量
     * @param like  模糊查询
     * @return list
     */
    @Override
    @Cacheable(key = "'user_cache' + (#like.equals('') ? '' : '_' + #like) + '_' + #index")
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public List<User> queryByPage(@FuncParameter("index") int index, @FuncParameter("count") int count, @FuncParameter("like") String like) {
        return userMapper.queryByPage(index, count, format("%?%", like));
    }

    /**
     * 查询全部用户
     *
     * @return List<User>
     */
    @Override
    @Cacheable(key = "'user_cache_all'")
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    /**
     * 删除用户
     * @param uid 用户id
     * @return int
     */
    @Override
    @CacheEvict(cacheNames = "user", allEntries = true)
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public int delete(@FuncParameter("uid") int uid) {
        User user = new User(uid);
        user.setDeleted(true);
        return userMapper.update(user);
    }

    /**
     * 修改用户
     *
     * @param user 修改后的属性
     * @return int
     */
    @Override
    @CacheEvict(cacheNames = "user", allEntries = true)
    @FuncLambda(classFile = UserService.class, refs = { "service", "watch-log" })
    public int update(@FuncParameter("user") User user) {
        return userMapper.update(user);
    }

}
