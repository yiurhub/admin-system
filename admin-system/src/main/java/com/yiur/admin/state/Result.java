package com.yiur.admin.state;

/**
 * 返回状态
 * @author Yiur
 */

public enum Result {

    /**
     * 返回成功 state => 1
     */
    OK("ok", 1),
    /**
     * 返回失败 state => -1
     */
    ERROR("error", -1),
    /**
     * 没有操作 state => 0
     */
    NULL("null", 0),
    /**
     * String类型空字符 => 0
     */
    EMPTY("", 0);

    /**
     * 返回状态的key String
     */
    public final String key;

    /**
     * 返回状态 int
     */
    public final int state;

    /**
     * 构造器
     * @param state 状态值
     * @param key 状态值key
     */
    Result(String key, int state) {
        this.key = key;
        this.state = state;
    }

}
