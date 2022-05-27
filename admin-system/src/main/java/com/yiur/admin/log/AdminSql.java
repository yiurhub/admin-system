package com.yiur.admin.log;

import com.yiur.admin.AdminSystemApplication;
import com.yiur.admin.lambda.logger.Logger;
import org.apache.ibatis.logging.Log;

/**
 * @author Yiur
 */
public class AdminSql implements Log {

    private final String clazz;

    private final LoggerBuilder loggerBuilder;

    private static class LoggerBuilder {

        private volatile Logger logger;
        private boolean debugEnabled;
        private boolean traceEnabled;

        public LoggerBuilder() {
            debugEnabled = true;
            traceEnabled = true;
        }

        public LoggerBuilder(boolean debugEnabled, boolean traceEnabled) {
            this.debugEnabled = debugEnabled;
            this.traceEnabled = traceEnabled;
        }

        public Logger build() {
            if (AdminSystemApplication.context != null && logger == null) {
                synchronized (LoggerBuilder.class) {
                    if (AdminSystemApplication.context != null && logger == null) {
                        logger = AdminSystemApplication.context.getBean(Logger.class);
                    }
                }
            }
            return logger;
        }

        public boolean isDebugEnabled() {
            return debugEnabled;
        }

        public void setDebugEnabled(boolean debugEnabled) {
            this.debugEnabled = debugEnabled;
        }

        public boolean isTraceEnabled() {
            return traceEnabled;
        }

        public void setTraceEnabled(boolean traceEnabled) {
            this.traceEnabled = traceEnabled;
        }

    }

    public AdminSql(String clazz) {
        this.clazz = clazz;
        this.loggerBuilder = new LoggerBuilder();
    }

    public String getClazz() {
        return clazz;
    }

    public LoggerBuilder getLoggerBuilder() {
        return loggerBuilder;
    }

    @Override
    public boolean isDebugEnabled() {
        return loggerBuilder.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return loggerBuilder.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable throwable) {
        if (loggerBuilder.build() != null) {
            loggerBuilder.build().error(s, throwable);
        }
    }

    @Override
    public void error(String s) {
        if (loggerBuilder.build() != null) {
            loggerBuilder.build().error(s);
        }
    }

    @Override
    public void debug(String s) {
        if (loggerBuilder.build() != null) {
            loggerBuilder.build().debug(s);
        }
    }

    @Override
    public void trace(String s) {
        if (loggerBuilder.build() != null) {
            loggerBuilder.build().info(s);
        }
    }

    @Override
    public void warn(String s) {
        if (loggerBuilder.build() != null) {
            loggerBuilder.build().warn(s);
        }
    }

}
