package com.yiur.admin.config;

import com.yiur.admin.filter.RoleFilter;
import com.yiur.admin.pojo.User;
import com.yiur.admin.service.UserService;
import com.yiur.admin.utils.PermsUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置
 * @author Yiur
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
        shiroFilterFactory.setSecurityManager(securityManager);

        // 自定义过滤器
        Map<String, Filter> filterHashMap = new HashMap<>();
        // 权限过滤器
        filterHashMap.put("role-perms", new RoleFilter());
        // 过滤器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 排除swagger
        filterChainDefinitionMap.put("/swagger-ui/index.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // websocket
        filterChainDefinitionMap.put("/websocket/**", "anon");
        // 排除url
        filterChainDefinitionMap.put("/code/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/login/reload", "anon");
        // 权限设置
        filterChainDefinitionMap.put("/history/**", "role-perms[root]");
        filterChainDefinitionMap.put("/user/get/**", "role-perms[root]");
        filterChainDefinitionMap.put("/user/add/**", "role-perms[root]");
        filterChainDefinitionMap.put("/user/delete/**", "role-perms[root]");
        filterChainDefinitionMap.put("/user/set/**", "role-perms[admin]");
        filterChainDefinitionMap.put("/upload/**", "role-perms[root|admin]");
        filterChainDefinitionMap.put("/**", "authc");
        // 添加过滤器
        shiroFilterFactory.setFilters(filterHashMap);
        // 添加过滤器链
        shiroFilterFactory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 登陆失败跳转路径
        shiroFilterFactory.setLoginUrl("/code/401");
        return shiroFilterFactory;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联Realm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 自定义UserRealm extends AuthorizingRealm
     */
    public static class UserRealm extends AuthorizingRealm {

        /**
         * 允许登录的权限
         */
        @Value("${login-perms}")
        private String loginPerms;

        /**
         * 用户service层
         */
        @Autowired
        private UserService userService;

        /**
         * 授权
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            //拿到当前登录的这个对象
            Subject subject = SecurityUtils.getSubject();
            //拿到user对象
            User currentUser = (User) subject.getPrincipal();

            //给当前用户授权
            info.addStringPermission(currentUser.getPerms());

            return info;
        }

        /**
         * 认证
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            //连接真实数据库
            User user = userService.query(new User(token.getUsername(), String.valueOf(token.getPassword())));

            //查询的用户是否空, 拥有是否拥有root或者admin权限
            if (user == null || !PermsUtil.parsePerms(loginPerms, PermsUtil.parseRoles(user.getPerms()))) {
                return null;
            }

            //shiro可以加密：MD5 MD5盐值加密 还可以通过UserRealm构造器中调用父类的构造器进行加密
            //shiro密码认证
            return new SimpleAuthenticationInfo(user, user.getPassword(), "");
        }

    }

}
