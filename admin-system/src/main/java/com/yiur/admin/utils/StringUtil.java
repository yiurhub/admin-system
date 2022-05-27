package com.yiur.admin.utils;

import java.util.UUID;

/**
 * String 工具类
 * @author Yiur
 */
public final class StringUtil {

    /**
     * 获取随机字符串
     * @return 随机字符串
     */
    public static String random() {
        return UUID.randomUUID().toString();
    }

}
