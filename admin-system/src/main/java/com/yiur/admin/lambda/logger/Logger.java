package com.yiur.admin.lambda.logger;

/**
 * 匿名函数托管的日志类
 * @author Yiur
 */
public interface Logger {

    /**
     * 输出普通的日志
     * @param message 日志消息
     */
    void info(String message);
    /**
     * 输出调试的日志
     * @param message 日志消息
     */
    void debug(String message);
    /**
     * 输出警告的日志
     * @param message 日志消息
     */
    void warn(String message);
    /**
     * 输出异常的日志
     * @param message 日志消息
     */
    void error(String message);
    /**
     * 输出异常的日志
     * @param message 日志消息
     * @param e 异常
     */
    void error(String message, Throwable e);

}
