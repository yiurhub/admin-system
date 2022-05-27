package com.yiur.admin.plugin;

import com.yiur.admin.utils.RedisUtil;
import org.func.spring.boot.component.plugin.AbstractFuncLoggerPlugin;
import org.func.spring.boot.data.FuncMethod;
import org.func.spring.boot.exception.FuncLoggerException;
import org.func.spring.boot.link.FuncLink;
import org.func.spring.boot.properties.FuncProperties;
import org.func.spring.boot.utils.FileUtil;

import java.io.File;
import java.io.IOException;

import static org.func.spring.boot.utils.StringUtil.*;

/**
 * 匿名函数日志插件
 * @author Yiur
 */
public class ServiceLoggerPlugin extends AbstractFuncLoggerPlugin {

    private RedisUtil redisUtil;

    private final String beanName;

    private final String alias;

    private final String[] refs;

    private final FuncLink funcLink;

    private final FuncProperties funcProperties;

    public ServiceLoggerPlugin(String beanName, String alias, String[] refs, FuncLink funcLink, FuncProperties funcProperties) {
        super(beanName, alias, refs, funcLink, funcProperties);
        this.beanName = beanName;
        this.alias = alias;
        this.refs = refs;
        this.funcLink = funcLink;
        this.funcProperties = funcProperties;
    }

    @Override
    public void write(String log, FuncMethod funcMethod) throws Throwable {
        redisUtil.lSet("service:history", log, 60 * 60 * 3);
        String path = funcMethod.getLogger().getPath();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String name = funcMethod.getLogger().getFileName();
        String suffix = funcMethod.getLogger().getFileSuffix();
        String filePath = format("?/?.?", path, name, suffix);

        try {
            FileUtil.write(funcMethod, filePath, log);
        } catch (IOException e) {
            throw new FuncLoggerException("Anonymous function log output error!");
        }
    }

}