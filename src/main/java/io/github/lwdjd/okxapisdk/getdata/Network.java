package io.github.lwdjd.okxapisdk.getdata;



import io.github.lwdjd.okxapisdk.account.SignatureUtil;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import static io.github.lwdjd.okxapisdk.getdata.PublicData.mainUrl;

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
                // 添加模拟交易的请求头
                .header("x-simulated-trading", "1")
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
                // 添加模拟交易的请求头
                .header("x-simulated-trading", "1")
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }
    // 不使用代理的POST请求
    public static String post(String url, String jsonBody) throws Exception {
        // 创建HttpClient对象
        HttpClient client = HttpClient.newHttpClient();

        // 创建一个HttpRequest对象，设置请求方法为POST，请求地址为url，并且带有JSON格式的请求体
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(jsonBody))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }

    // 使用代理的POST请求
    public static String post(String url, String jsonBody, String httpProxy) throws Exception {
        // 解析代理参数，获取代理服务器的地址和端口
        String[] httpParts = httpProxy.split(":");
        String httpHost = httpParts[0];
        int httpPort = Integer.parseInt(httpParts[1]);

        // 创建一个ProxySelector对象，用于指定HTTP代理
        ProxySelector proxySelector = ProxySelector.of(new InetSocketAddress(httpHost, httpPort));

        // 创建一个HttpClient对象，设置代理选择器为proxySelector
        HttpClient client = HttpClient.newBuilder()
                .proxy(proxySelector)
                .build();

        // 创建一个HttpRequest对象，设置请求方法为POST，请求地址为url，并且带有JSON格式的请求体
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(jsonBody))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }

    /**
     *带有密钥和签名的POST请求
     *
     * @param requestPath 请求路径
     * @param apiKey 你的KEY
     * @param secretKey 你的密钥key
     * @param passphrase 你的密码
     * @param timestamp UTC时间
     * @param body 主要post内容
     * @return 接收到的内容
     * @throws Exception 抛出意外的错误
     */
    public static String postWithSignature(String requestPath, String apiKey, String secretKey, String passphrase, String timestamp, String body,Boolean simulatedTrading) throws Exception {
        // 创建签名
        String sign = SignatureUtil.sign(timestamp, "POST",requestPath, body, secretKey);

        // 创建HttpClient对象
        HttpClient client = HttpClient.newHttpClient();

        // 创建一个HttpRequest对象，设置请求方法为POST，请求地址为url，并且带有JSON格式的请求体
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(body))
                .uri(URI.create(mainUrl+requestPath))
                .header("OK-ACCESS-KEY", apiKey)
                .header("OK-ACCESS-SIGN", sign)
                .header("OK-ACCESS-TIMESTAMP", timestamp)
                .header("OK-ACCESS-PASSPHRASE", passphrase)
                .header("Content-Type", "application/json")
                // 添加模拟交易的请求头
                .header("x-simulated-trading",simulatedTrading ? "1" : "0")
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }

    /**
     *带有密钥和签名的代理POST请求
     *
     * @param requestPath 请求路径
     * @param apiKey 你的KEY
     * @param secretKey 你的密钥key
     * @param passphrase 你的密码
     * @param timestamp UTC时间
     * @param body 主要post内容
     * @param httpProxy 代理，格式"IP:PORT"
     * @return 接收到的内容
     * @throws Exception 抛出意外的错误
     */
    public static String postWithSignature(String requestPath, String apiKey, String secretKey, String passphrase, String timestamp, String body,Boolean simulatedTrading, String httpProxy) throws Exception {
        // 创建签名
        String sign = SignatureUtil.sign(timestamp, "POST", requestPath, body, secretKey);

        //
        String proxyHost = httpProxy.split(":")[0];
        int proxyPort = Integer.parseInt(httpProxy.split(":")[1]);

        // 创建一个ProxySelector对象，用于指定HTTP代理
        ProxySelector proxySelector = ProxySelector.of(new InetSocketAddress(proxyHost, proxyPort));

        // 创建一个HttpClient对象，设置代理选择器
        HttpClient client = HttpClient.newBuilder()
                .proxy(proxySelector)
                .build();

        // 创建一个HttpRequest对象，设置请求方法为POST，请求地址为url，并且带有JSON格式的请求体
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(mainUrl+requestPath))
                .header("OK-ACCESS-KEY", apiKey)
                .header("OK-ACCESS-SIGN", sign)
                .header("OK-ACCESS-TIMESTAMP", timestamp)
                .header("OK-ACCESS-PASSPHRASE", passphrase)
                .header("Content-Type", "application/json")
                // 添加模拟交易的请求头
                .header("x-simulated-trading",simulatedTrading ? "1" : "0")
                .build();

        // 发送请求，获取响应对象
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的字符串内容
        return response.body();
    }

}
