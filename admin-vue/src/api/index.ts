import request from '../utils/request';
import router from "../router";
import {ElMessage, ElMessageBox} from "element-plus";
import {websocket} from "../plugins/websocket";
import {Method} from "axios";

// websocket callback
export const websocketCallback: Map<string, Map<string, Function>> = new Map<string, Map<string, Function>>();
// add websocket callback
export const addWebsocket = (key:string, logo: string, lambda: Function) => {
    let functions: Map<string, Function> = websocketCallback.get(key);
    if (functions === undefined) {
        functions = new Map<string, Function>();
    }
    if (functions.get(logo) !== undefined || functions.get(logo) !== null) {
        functions.set(logo, lambda);
        websocketCallback.set(key, functions);
    }
}

// 对话框
export const confirm = (title: string, content: string, callback: Function) => {
    ElMessageBox.confirm(content, title, {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then((done: any) => {
        callback();
        done();
    }).catch(() => {});
};

// 请求
export const http: <T> ({url, method, data, params, config}: {url: string|any, method: Method|any, data?: T|any, params?: T|any, config?: any}) => Promise<any> = ({url, method, data, params, config}) => {
    return request.request({
        url: url,
        method: method,
        data: data,
        params: params,
        headers: config
    });
};
// get请求
export const get: <T> (url: string, data: T|any, callback?: Function, error?: Function) => Promise<any> = (url, data, callback, error) => {
    return http({
        url: url,
        method: 'get',
        params: data
    }).then((resp) => {
        callback(resp);
    }).catch((e) => {
        error(e);
    });
}
// post请求
export const post: <T> (url: string, data: T|any, callback?: Function, error?: Function) => Promise<any> = (url, data, callback, error) => {
    return http({
        url: url,
        method: 'post',
        data: data
    }).then((resp) => {
        callback(resp);
    }).catch((e) => {
        error(e);
    });
}

// 切换语言环境
export const toggleI18nLocal = (i18n: any, url: string) => {
    i18n.locale = i18n.locale === 'zh-cn' ? 'en' : 'zh-cn';
}

// 是否登陆
export const reload = () => {
    get('/api/login/reload', null, (resp) => { if (!resp) return anewLogin(); }, () => anewLogin());
};

// 重新登陆
const anewLogin = () => {
    sessionStorage.removeItem("user");
    router.push(`/login`).then(r => r);
    ElMessage.error("请重新登录");
};

// 注销
export const logout = (message?: string, callback?: Function, data?: any) => {
    onClose(message === undefined ? "注销成功" : message, callback, data);
};

export const onClose = (message?: string, callback?: Function, data?: any) => {
    get('/api/logout', data, (resp) => {
        if (callback !== undefined) {
            return callback(resp);
        }
        closeDefaultCallback(resp, message);
    })
};

// close默认回调
const closeDefaultCallback = (resp?: number, message?: string) => {
    if (resp !== 1) {
        ElMessage.error("请在试一遍");
    } else {
        websocket.close();
        sessionStorage.removeItem("user");
        router.push(`/login`).then(r => r);
        ElMessage.warning(message);
    }
};