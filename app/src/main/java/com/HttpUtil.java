package com;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/**
 * @author Sage
 * @time 2020/11/4 22:38
 * @description 网络操作公共工具类 HttpUtil。发起HTTP请求
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
