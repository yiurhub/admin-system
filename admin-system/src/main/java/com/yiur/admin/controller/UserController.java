package com.yiur.admin.controller;

import com.yiur.admin.pojo.Page;
import com.yiur.admin.pojo.Perms;
import com.yiur.admin.pojo.User;
import com.yiur.admin.server.WebSocketServer;
import com.yiur.admin.service.PermsService;
import com.yiur.admin.service.UserService;
import com.yiur.admin.state.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author Yiur
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * root name
     */
    @Value("${root-username}")
    private String root;
    /**
     * 权限service层
     */
    @Autowired
    private PermsService permsService;
    /**
     * 用户service层
     */
    @Autowired
    private UserService userService;
    /**
     * 获取用户连接信息
     */
    private final Map<String, WebSocketServer> webSocketMap = WebSocketServer.getWebSocketMap();

    /**
     * 获取分页总数量
     * @return int
     */
    @ApiOperation("获取分页信息")
    @GetMapping("/get/page")
    public Page getPageTotal(@RequestParam("pageCount") int pageCount, @RequestParam("pageLike") String pageLike) {
        return new Page(pageCount, userService.getTotal(pageLike));
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return int
     */
    @ApiOperation("添加用户")
    @PostMapping("/add/user")
    public int addUser(@RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 获取全部用户
     * @return List
     */
    @ApiOperation("获取全部用户")
    @GetMapping("/get/all")
    public List<User> getUsers() {
        return setUserOnline(userService.queryAll());
    }

    /**
     * 获取用户分页数据
     * @return List
     */
    @ApiOperation("获取用户分页数据")
    @PostMapping("/get/page")
    public List<User> getUsersByPage(@RequestBody Page page) {
        return setUserOnline(userService.queryByPage(page.getPageIndex(), page.getPageCount(), page.getPageLike()));
    }

    /**
     * 设置用户登录状态
     * @param users 用户集合
     * @return List
     */
    private List<User> setUserOnline(@RequestBody List<User> users) {
        for (User user : users) {
            user.setOnline(webSocketMap.get(user.getUsername()) != null);
        }
        return users;
    }

    /**
     * 获取全部权限
     * @return List
     */
    @ApiOperation("获取全部权限")
    @GetMapping("/get/perms/all")
    public List<Perms> getPerms() {
        return permsService.queryAll();
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return int
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/set/user")
    public int updateUser(@RequestBody User user) {
        if (userService.update(user) == 1) {
            if (webSocketMap.get(root) != null) {
                webSocketMap.get(root).sendMessage("CLOSE");
            }
            SecurityUtils.getSubject().logout();
            return Result.OK.state;
        }
        return Result.ERROR.state;
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return int
     */
    @ApiOperation("修改用户权限")
    @PostMapping("/set/list/user")
    public int updateListUser(@RequestBody User user) {
        if (userService.update(user) == 1) {
            if (webSocketMap.get(user.getUsername()) != null) {
                webSocketMap.get(user.getUsername()).sendMessage("RELOAD");
            }
            return Result.OK.state;
        }
        return Result.ERROR.state;
    }

    /**
     * 删除用户
     * @param uid 根据id删除
     * @return int
     */
    @ApiOperation("删除用户")
    @GetMapping("/delete/{uid}")
    public int deleteUser(@PathVariable("uid") int uid) {
        return userService.delete(uid);
    }

}
