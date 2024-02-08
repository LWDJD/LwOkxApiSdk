package io.github.lwdjd.okxapisdk.getdata;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Address;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;

/**
* get类用于发送GET请求
*/
public class Network {
    private static final OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws Exception{
        try{
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code "+ response);
            }
            return response.body().string();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * 使用代理发送GET请求
     * @param url 请求的url
     * @return 返回请求的结果
     * @throws Exception
     */
    public static String getWithProxy(String url,String proxy) throws Exception{
        try{
            //创建 OkHttp3的 client对象,并设置代理服务器+
            String[] pa = proxy.split(":");
            String proxyHost = pa[0];
            int proxyPort = Integer.parseInt(pa[1]);
            SocketAddress sa = new java.net.InetSocketAddress(proxyHost, proxyPort);
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().proxy(new Proxy(Proxy.Type.SOCKS,sa)).build();

            //发送 GET请求
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()){
                throw new IOException("错误代码："+ response);
            }

            return response.body().string();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }



}
