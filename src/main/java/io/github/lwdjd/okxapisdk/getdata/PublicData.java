package io.github.lwdjd.okxapisdk.getdata;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.github.lwdjd.okxapisdk.parsingjson.PublicData.parsingMarkPrice;
import static io.github.lwdjd.okxapisdk.parsingjson.PublicData.parsingMarkPriceCandles;

/**
 * @author lwdjd
 * 公开数据获取
 */
public class PublicData {
    public static String httpProxy = "";
    public final static String MARGIN = "MARGIN";//币币、杠杆
    public final static String SWAP = "SWAP";//永续合约
    public final static String FUTURES = "FUTURES";//交割合约
    public final static String OPTION = "OPTION";//期权
    public final static String mainUrl = "https://www.okx.com";
    public final static String markPriceUrl = "/api/v5/public/mark-price";
    public final static String markPriceCandlesUrl = "/api/v5/market/mark-price-candles";

    /**
     * 获取标记价格
     * 详细文档链接：<a href="https://www.okx.com/docs-v5/zh/#public-data-rest-api-get-mark-price">...</a>
     *
     * @param instType   (必填)产品类型:
     *                       MARGIN：币币杠杆
     *                       SWAP：永续合约
     *                       FUTURES：交割合约
     *                      OPTION：期权
     * @param uly        标的指数:适用于交割/永续/期权
     * @param instFamily 交易品种:适用于交割/永续/期权
     * @param instId     产品ID，如 BTC-USD-SWAP
     * @return 返回指定合约的标记价格,格式为List<Map<String, Object>>
     * @throws Exception 抛出异常
     */
    public static List<Map<String, Object>> getMarkPrice(String instType, String uly, String instFamily, String instId) throws Exception {
        // 获取标记价格的URL
        String url = getMarkPriceBody(instType,uly,instFamily,instId);
        // 如果http代理为空，则不使用代理获取数据，并处理json数据后返回
        if (Objects.equals(httpProxy, "")){
            return parsingMarkPrice(Network.get(url));
        }
        // 如果http代理不为空，则使用代理获取数据，并处理json数据后返回
        return parsingMarkPrice(Network.get(url,httpProxy));
    }
    /**
     * 根据传入的参数生成一个MarkPrice的URL。
     * 详细文档链接：<a href="https://www.okx.com/docs-v5/zh/#public-data-rest-api-get-mark-price">...</a>
     *
     * @param instType 产品类型(必填)
     * @param uly 标的指数
     * @param instFamily 交易品种
     * @param instId 产品ID
     * @return 返回生成的URL，如果instType为null则返回null
     */
    private static String getMarkPriceBody(String instType, String uly, String instFamily, String instId) throws Exception {
        String url = mainUrl+markPriceUrl;
        if (instType!=null){// 检查instType是否为null，如果为null则直接抛出异常
            // 将instType参数添加到url中
            url=url+ "?"+
                "instType=" + URLEncoder.encode(instType, StandardCharsets.UTF_8);
        }else
        {
            throw new Exception("instType禁止为空");
        }
        // 检查uly是否为null，如果不为null则添加到url中
        if (uly!=null){
            url=url+"&uly="+URLEncoder.encode(uly, StandardCharsets.UTF_8);
        }
        // 检查instFamily是否为null，如果不为null则添加到url中
        if(instFamily!=null){
            url=url+"&instFamily="+URLEncoder.encode(instFamily, StandardCharsets.UTF_8);
        }
        // 检查instId是否为null，如果不为null则添加到url中
        if(instId!=null){
            url=url+"&instId="+URLEncoder.encode(instId, StandardCharsets.UTF_8);
        }
        return url;
    }

    /**
     * 获取标记价格K线数据
     * 详细文档链接：<a href="https://www.okx.com/docs-v5/zh/#public-data-rest-api-get-mark-price-candlesticks">...</a>
     *
     * @param instId	String	是	产品ID，如BTC-USD-SWAP
     * @param after 	String	否	请求此时间戳之前（更旧的数据）的分页内容，传的值为对应接口的ts
     * @param before	String	否	请求此时间戳之后（更新的数据）的分页内容，传的值为对应接口的ts, 单独使用时，会返回最新的数据。
     * @param bar       String	否	时间粒度，默认值1m
     * 如 [1m/3m/5m/15m/30m/1H/2H/4H]
     * 香港时间开盘价k线：[6H/12H/1D/1W/1M/3M]
     * UTC时间开盘价k线：[6Hutc/12Hutc/1Dutc/1Wutc/1Mutc/3Mutc]
     * @param limit 	String	否	分页返回的结果集数量，最大为100，不填默认返回100条
     * @return 标记价格K线数据列表
     * 0    String	开始时间，Unix时间戳的毫秒数格式，如 1597026383085
     * 1	String	开盘价格
     * 2	String	最高价格
     * 3	String	最低价格
     * 4	String	收盘价格
     * 5	String	K线状态 0 代表 K 线未完结，1 代表 K 线已完结。
     */
    public static List<List<String>> getMarkPriceCandles(String instId,String after,String before,String bar,String limit) throws Exception {
        String url=getMarkPriceCandlesBody(instId,after,before,bar,limit);
        // 如果http代理为空，则不使用代理获取数据，并处理json数据后返回
        if (Objects.equals(httpProxy, "")){
            return parsingMarkPriceCandles(Network.get(url));
        }
        // 如果http代理不为空，则使用代理获取数据，并处理json数据后返回
        return parsingMarkPriceCandles(Network.get(url,httpProxy));
    }
/**
 * 根据传入的参数生成一个MarkPriceCandles的URL。
 * 详细文档链接：<a href="https://www.okx.com/docs-v5/zh/#public-data-rest-api-get-mark-price-candlesticks">...</a>
 * @return 完整Url;
 */

    private static String getMarkPriceCandlesBody(String instId,String after,String before,String bar,String limit) throws Exception {
        String url = mainUrl+markPriceCandlesUrl;
        if (instId!=null){// 检查instId是否为null，如果为null则直接抛出异常
            // 将instId参数添加到url中
            url=url+ "?"+
                    "instId=" + URLEncoder.encode(instId, StandardCharsets.UTF_8);
        }else
        {
            throw new Exception("instId禁止为空");
        }
        if (after!=null){
            url=url+"&after="+URLEncoder.encode(after, StandardCharsets.UTF_8);
        }
        if (before!=null){
            url=url+"&before="+URLEncoder.encode(before, StandardCharsets.UTF_8);
        }
        if (bar!=null){
            url=url+"&bar="+URLEncoder.encode(bar, StandardCharsets.UTF_8);
        }
        if (limit!=null){
            url=url+"&limit="+URLEncoder.encode(limit, StandardCharsets.UTF_8);
        }
        return url;
    }
}
