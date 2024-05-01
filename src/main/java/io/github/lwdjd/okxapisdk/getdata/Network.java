package io.github.lwdjd.okxapisdk.getdata;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketAddress;

/**
* get类用于发送GET请求
*/
public class Network {
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 使用代理发送GET请求
     * @param url 请求的url
     * @return 返回请求的结果
     */
    public static String get(String url) throws Exception{
        try{
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code "+ response);
            }
            if(response.body() != null) {
                return response.body().string();
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * 使用代理服务器发送GET请求并获取响应数据
     *
     * @param url 请求的url
     * @param proxy  返回响应数据，如果响应体为空，则返回null
     * @return 返回请求的结果
     * @throws Exception 如果请求失败或响应体为空，则抛出异常
     */
    public static String getWithProxy(String url,String proxy) throws Exception{
        try{
            String[] pa = proxy.split(":");// 将代理服务器地址字符串按":"分割，得到数组
            String proxyHost = pa[0];// 获取代理服务器的主机名
            int proxyPort = Integer.parseInt(pa[1]);// 获取代理服务器的端口号，并将字符串转为整数
            SocketAddress sa = new java.net.InetSocketAddress(proxyHost, proxyPort);// 创建一个SocketAddress对象，包含代理服务器的主机名和端口号
            client.newBuilder().proxy(new Proxy(Proxy.Type.SOCKS,sa)).build();// 使用OkHttpClient的newBuilder方法创建一个新的Builder对象，并设置代理服务器

            //发送 GET请求
            Request request = new Request.Builder().url(url).build();// 创建一个新的Request对象，设置请求的url
            Response response = client.newCall(request).execute();// 使用OkHttpClient对象发送请求，并获取响应结果

            if (!response.isSuccessful()){
                throw new IOException("错误代码："+ response);
            }
            if(response.body() != null) {
                return response.body().string();// 返回响应数据
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
