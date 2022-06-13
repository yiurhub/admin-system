package com.yiur.admin.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yiur.admin.lambda.logger.Logger;
import com.yiur.admin.pojo.User;
import com.yiur.admin.utils.RedisUtil;
import org.func.spring.boot.annotation.EnableFuncLambda;
import org.func.spring.boot.annotation.LogFilter;
import org.func.spring.boot.builder.FuncLinkBuilder;
import org.func.spring.boot.component.callback.FuncCallback;
import org.func.spring.boot.component.life.FuncLife;
import org.func.spring.boot.component.logger.FuncLogger;
import org.func.spring.boot.component.watch.FuncWatch;
import org.func.spring.boot.link.AbstractFuncLink;
import org.func.spring.boot.link.FuncLink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * 匿名函数链接配置
 * @author Yiur
 */
@Configuration
@EnableFuncLambda("com.yiur.admin")
public class FuncLinkConfig {

    @Bean
    public FuncLink funcLink(FuncLinkBuilder funcLinkBuilder, Watch watch,
                             ServiceLife<Object, Object> serviceLife,
                             ServiceCallback<Object, Object> serviceCallback) {
        return funcLinkBuilder.create()
                // watch
                .<FuncWatch>setObject("watch-log", AbstractFuncLink.WATCH, watch)
                // logger
                .<FuncLogger>setObject("logger", AbstractFuncLink.LOG, () -> "${dateTime}\t${methodName.toUpperCase()}\t%s\r\n")
                // upload
                .<FuncLogger>setObject("upload", AbstractFuncLink.LOG, () -> "[upload] [日期:${dateTime}] 执行是否成功:${invokeResult} %s\r\n")
                // service
                .<FuncLogger>setObject("service", AbstractFuncLink.LOG, () -> "#\t${methodName}\r\n{ \"target\": \"service\", \"dateTime\": \"${dateTime}\", \"invokeResult\": \"${invokeResult}\", \"method\": \"${methodName}(${parameterSource})\" }\r\n#\tresult\r\n%s\r\n#\tend\r\n")
                .setObject("service", AbstractFuncLink.LIFE, serviceLife)
                .setObject("service", AbstractFuncLink.CALLBACK, serviceCallback);
    }

    @Component
    record Watch (RedisUtil redisUtil) implements FuncWatch {

        @LogFilter("logger")
        public String logger(String log, Map<String, Object> data, Object result) {
            data.put("logger:result", result);
            String message = (String) data.get("message");
            if (data.get("args") != null) {
                message = String.format(message, Arrays.toString((Object[]) data.get("args")));
            }
            return String.format(log, message);
        }

        @LogFilter("service")
        public String service(String log, Map<String, Object> data, Object result) {
            data.put("service:result", result);
            String format = String.format(log, JSON.toJSONString(data, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
            redisUtil.lSet("service:history", format, 60 * 60 * 3);
            return format;
        }


        @LogFilter("upload")
        public String upload(String log, Map<String, Object> data, Object result) {
            data.put("upload:result", result);
            return String.format(log, data.get("fileName"));
        }

    }

    /**
     * setObject("service", AbstractFuncLink.LIFE, serviceLife)
     * 加上@Lazy注解解决Logger的循环引用
     */
    @Component
    record ServiceLife<T, R> (@Lazy Logger logger) implements FuncLife<T, R> {

        @Override
        public Map<String, Object> start(Map<String, Object> args) throws Exception {
            return args;
        }

        @Override
        public R end(T result) throws Exception {
            return (R) result;
        }

    }

    /**
     * 加上@Lazy注解解决Logger的循环引用
     */
    @Component
    record ServiceCallback<T, R> (@Lazy Logger info) implements FuncCallback<T, R> {

        @Override
        public R then(T result) {
            if (result instanceof User user) {
                info.info("用户登录:%s", user);
            }
            return (R) result;
        }

        @Override
        public R error(Throwable e) {
            e.printStackTrace();
            return null;
        }

    }

}
