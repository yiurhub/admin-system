package com.yiur.admin.controller;

import com.yiur.admin.pojo.User;
import com.yiur.admin.server.WebSocketServer;
import com.yiur.admin.service.UserService;
import com.yiur.admin.state.Result;
import com.yiur.admin.utils.IPUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 登陆管理
 * @author Yiur
 */
@RestController
public class LoginController {

    /**
     * root的用户名
     */
    @Value("${root-username}")
    private String root;
    /**
     * ip 工具类
     */
    @Autowired
    private IPUtil ipUtil;
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
     * 测试资源是否成功
     */
    @ApiOperation("是否登陆")
    @GetMapping("/login/reload")
    public boolean whetherToLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 登陆
     */
    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public User login(HttpServletRequest request, @RequestBody User login) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装当前用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), login.getPassword());

        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            String address = ipUtil.getAddress(ipUtil.getIpAddress(request));
            userService.update(new User(user.getUid(), address, new Date()));
            if (webSocketMap.get(root) != null) {
                webSocketMap.get(root).sendMessage("PING");
            }
            return user;
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return null;
        }
    }

    /**
     * 注销
     */
    @ApiOperation("用户注销")
    @GetMapping("/logout")
    public int login() {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        if (webSocketMap.get(root) != null) {
            webSocketMap.get(root).sendMessage("CLOSE");
        }
        return Result.OK.state;
    }

    /**
     * 注销
     */
    @ApiOperation("用户注销")
    @GetMapping("/logout/{username}")
    public int login(@PathVariable("username") String username) {
        if (webSocketMap.get(username) != null) {
            webSocketMap.get(username).sendMessage("RELOAD");
        }
        return Result.OK.state;
    }

}
