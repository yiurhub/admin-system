package com.yiur.admin.plugin;

import org.func.spring.boot.component.callback.FuncCallback;
import org.func.spring.boot.component.callback.FuncCallbackError;
import org.func.spring.boot.component.callback.FuncCallbackThen;
import org.func.spring.boot.component.callback.SimpleFuncCallback;
import org.func.spring.boot.component.plugin.AbstractFuncCallbackPlugin;
import org.func.spring.boot.component.plugin.FuncLoggerPlugin;
import org.func.spring.boot.data.FuncMethod;
import org.func.spring.boot.link.FuncLink;
import org.func.spring.boot.properties.FuncProperties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yiur
 */
public class ServiceCallbackPlugin extends AbstractFuncCallbackPlugin {

    private final String beanName;

    private final String alias;

    private final String[] refs;

    private final FuncLink funcLink;

    private final FuncProperties funcProperties;

    private final FuncLoggerPlugin funcLoggerPlugin;

    public ServiceCallbackPlugin(String beanName, String alias, String[] refs, FuncLink funcLink, FuncProperties funcProperties, FuncLoggerPlugin funcLoggerPlugin) {
        super(beanName, alias, refs, funcLink, funcProperties, funcLoggerPlugin);
        this.beanName = beanName;
        this.alias = alias;
        this.refs = refs;
        this.funcLink = funcLink;
        this.funcProperties = funcProperties;
        this.funcLoggerPlugin = funcLoggerPlugin;
    }

}
