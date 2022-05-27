package com.yiur.admin.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义权限认证
 * @author Yiur
 */
public class RoleFilter extends AuthorizationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        String[] permsArray = (String[]) mappedValue;

        boolean isPermitted = true;
        if (permsArray != null && permsArray.length > 0) {
            isPermitted = parseRole(permsArray, subject);
        }

        return isPermitted;
    }

    /**
     * 更具权限数组解析角色
     * @param permsArray 权限数组
     * @param subject 当前用户信息
     * @return boolean
     */
    public boolean parseRole(String[] permsArray, Subject subject) {
        boolean isPermitted = false;

        for (String perms : permsArray) {
            // 根据或者解析角色权限
            if (perms.contains("|")) {
                isPermitted = parseRoleByPerhaps(perms, subject);
                continue;
            }
            isPermitted = parseRoleByPerhaps(perms, subject);
            break;
        }

        return isPermitted;
    }

    /**
     * 根据或者解析角色权限
     * @param perms 角色权限
     * @param subject 当前用户信息
     * @return boolean
     */
    public boolean parseRoleByPerhaps(String perms, Subject subject) {
        boolean isPermitted = false;

        String[] roles = perms.split("\\|");
        for (boolean flag : subject.isPermitted(roles)) {
            if (flag) {
                isPermitted = true;
                break;
            }
        }

        return isPermitted;
    }

}