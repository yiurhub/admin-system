import {Method} from "axios";
import request from '../utils/request';

/**
 * http
 * @author Yiur
 */
export class Http {

    public url: string;
    public method: Method;
    public data: any;

    constructor(args: {url: string, method: Method, data: any}) {
        this.url = args.url;
        this.method = args.method;
        this.data = args.data;
    }

    // 请求
    public static http<T> ({url, method, data}: {url: string, method: Method, data?: T}): Promise<any> {
        return request({
            url: url,
            method: method,
            params: data
        });
    };

    // get请求
    public static get<T>(url: string, data: T, callback?: Function, error?: Function): Promise<any> {
        return this.http({
            url: url,
            method: 'get',
            data: data
        }).then((resp) => {
            callback(resp);
        }).catch((e) => {
            error(e);
        });
    }

    // post请求
    public static post<T>(url: string, data: T, callback?: Function, error?: Function): Promise<any> {
        return this.http({
            url: url,
            method: 'post',
            data: data
        }).then((resp) => {
            callback(resp);
        }).catch((e) => {
            error(e);
        });
    }

}