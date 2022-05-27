package com.yiur.admin.utils;

/**
 * 权限解析工具类
 * @author Yiur
 */
public final class PermsUtil {

    /**
     * 关于分隔符
     */
    public static final String ABOUT_DELIMITERS = ",";

    /**
     * 是否拥有权限
     * @param perms 角色必须拥有权限
     * @param roles 角色权限
     * @return boolean
     */
    public static boolean parsePerms(String perms, String... roles) {
        boolean isPermitted = false;

        for (String role : roles) {
            if (perms.contains(role)) {
                isPermitted = true;
                break;
            }
        }

        return isPermitted;
    }

    /**
     * 获取角色数组
     * @param roles 角色字符串
     * @return String[]
     */
    public static String[] parseRoles(String roles) {
        return roles.split(ABOUT_DELIMITERS);
    }

}
