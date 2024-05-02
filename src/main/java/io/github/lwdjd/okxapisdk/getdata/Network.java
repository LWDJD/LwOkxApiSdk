package io.github.lwdjd.okxapisdk.getdata;



import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
* get类用于发送GET请求
*/
public class Network {


    /**
     * 使用代理发送GET请求
     * @param url 请求的url
     * @return 返回请求的结果
     */
    public static String get(String url) throws Exception {
        // 创建一个HttpClient对象
        HttpClient client = HttpClient.newHttpClient();

        // 创建一个HttpRequest对象，设置请求方法为GET，请求地址为url加上params
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();

    }

    /**
     * 使用代理服务器发送GET请求并获取响应数据
     *
     * @param url 请求的url
     * @param httpProxy  返回响应数据，如果响应体为空，则返回null
     * @return 返回请求的结果
     * @throws Exception 如果请求失败或响应体为空，则抛出异常
     */
    public static String get(String url, String httpProxy) throws Exception{

        // 解析Socks参数，获取代理服务器的地址和端口
        String[] httpParts = httpProxy.split(":");
        String httpHost = httpParts[0];
        int httpPort = Integer.parseInt(httpParts[1]);

        // 创建一个ProxySelector对象，用于指定Http代理
        ProxySelector proxySelector = ProxySelector.of(new InetSocketAddress(httpHost, httpPort));

        // 创建一个HttpClient对象，设置代理选择器为proxySelector
        HttpClient client = HttpClient.newBuilder()
                .proxy(proxySelector)
                .build();

        // 创建一个HttpRequest对象，设置请求方法为GET，请求地址为url加上params
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }

}
