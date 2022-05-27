package com.yiur.admin.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yiur.admin.plugin.ServiceCallbackPlugin;
import com.yiur.admin.plugin.ServiceLoggerPlugin;
import com.yiur.admin.pojo.User;
import com.yiur.admin.utils.RedisUtil;
import org.func.spring.boot.annotation.EnableFuncLambda;
import org.func.spring.boot.annotation.ParseLog;
import org.func.spring.boot.builder.FuncLinkBuilder;
import org.func.spring.boot.component.AbstractFuncLink;
import org.func.spring.boot.component.callback.FuncCallbackError;
import org.func.spring.boot.component.callback.FuncCallbackThen;
import org.func.spring.boot.component.life.FuncLifeEnd;
import org.func.spring.boot.component.life.FuncLifeStart;
import org.func.spring.boot.component.logger.FuncLogger;
import org.func.spring.boot.component.watch.FuncWatch;
import org.func.spring.boot.link.FuncLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 匿名函数链接配置
 * @author Yiur
 */
@Configuration
@EnableFuncLambda("com.yiur.admin")
public class FuncLinkConfig {

    @Autowired
    private RedisUtil redisUtil;

    @Bean
    public FuncLink funcLink(FuncLinkBuilder funcLinkBuilder, Watch watch) {
        return funcLinkBuilder.create()
                // set variable
                .setVariable(RedisUtil.class, redisUtil)
                // watch
                .<FuncWatch>setObject("watch-log", AbstractFuncLink.WATCH, watch)
                // logger
                .setPlugin("logger", AbstractFuncLink.FUNC_CALLBACK_PLUGIN, ServiceCallbackPlugin.class)
                .<FuncLogger>setObject("logger", AbstractFuncLink.LOG, set -> "${dateTime}\t${methodName.toUpperCase()}\t%s\r\n")
                // upload
                .<FuncLogger>setObject("upload", AbstractFuncLink.LOG, set -> "[upload] [日期:${dateTime}] 执行是否成功:${invokeResult} %s\r\n")
                // service
                .setPlugin("service", AbstractFuncLink.FUNC_CALLBACK_PLUGIN, ServiceCallbackPlugin.class)
                .setPlugin("service", AbstractFuncLink.FUNC_LOGGER_PLUGIN, ServiceLoggerPlugin.class)
                .<FuncLogger>setObject("service", AbstractFuncLink.LOG, set -> "#\t${methodName}\r\n{ \"target\": \"service\", \"dateTime\": \"${dateTime}\", \"invokeResult\": \"${invokeResult}\", \"method\": \"${methodName}(${parameterSource})\" }\r\n#\tresult\r\n%s\r\n#\tend\r\n")
                .<FuncLifeStart>setObject("service", AbstractFuncLink.LIFE_START, args -> args)
                .<FuncLifeEnd<Object, Object>>setObject("service", AbstractFuncLink.LIFE_END, data -> data)
                .<FuncCallbackThen<Object, Object>>setObject("service", AbstractFuncLink.CALLBACK_THEN, result -> result)
                .<FuncCallbackError<Object>>setObject("service", AbstractFuncLink.CALLBACK_ERROR, Throwable::getMessage);
    }

    @Component
    static class Watch implements FuncWatch {

        public void value(Object old, Object value) {
        }

        @ParseLog("logger")
        public String logger(String log, Map<String, Object> data, Object result) {
            data.put("logger:result", result);
            return String.format(log, data.get("message"));
        }

        @ParseLog("service")
        public String service(String log, Map<String, Object> data, Object result) {
            if (data.get("user") instanceof User user) {
            }
            data.put("service:result", result);
            return String.format(log, JSON.toJSONString(data, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
        }


        @ParseLog("upload")
        public String upload(String log, Map<String, Object> data, Object result) {
            data.put("upload:result", result);
            return String.format(log, data.get("fileName"));
        }

    }

}
