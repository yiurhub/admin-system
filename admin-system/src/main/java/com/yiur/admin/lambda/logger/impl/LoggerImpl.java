package com.yiur.admin.lambda.logger.impl;

import com.yiur.admin.lambda.logger.Logger;
import org.func.spring.boot.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Yiur
 */
@FuncBean
@Component
@FuncRefs({ "logger", "watch-log" })
@FuncLogger(name = "admin-system-sql")
@FuncLambda(value = Logger.class)
public class LoggerImpl implements Logger {

    @Override
    public void info(String message) {
    }

    @Override
    public void debug(String message) {
    }

    @Override
    public void warn(String message) {
    }

    @Override
    public void error(String message) {
    }

    @Override
    public void error(String message, Throwable e) {
    }

}
