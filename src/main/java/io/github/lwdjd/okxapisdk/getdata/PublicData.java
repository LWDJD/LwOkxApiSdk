package io.github.lwdjd.okxapisdk.getdata;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.github.lwdjd.okxapisdk.parsingjson.PublicData.parsingMarkPrice;

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
    public static String mainUrl = "https://www.okx.com";
    public static String markPriceurlUrl = "/api/v5/public/mark-price";

    /**
     * 获取指定合约的标记价格
     * 详细文档链接：<a href="https://www.okx.com/docs-v5/zh/#public-data-rest-api-get-mark-price">...</a>
     *
     * @param instType   产品类型:
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
     * @param instType 产品类型
     * @param uly 标的指数
     * @param instFamily 交易品种
     * @param instId 产品ID
     * @return 返回生成的URL，如果instType为null则返回null
     */
    public static String getMarkPriceBody(String instType, String uly, String instFamily, String instId){
        String url;
        if (instType!=null){// 检查instType是否为null，如果为null则直接返回null
            // 将instType参数添加到url中
            url=mainUrl+markPriceurlUrl+ "?"+
                "instType=" + URLEncoder.encode(instType, StandardCharsets.UTF_8);
        }
        else {
            return null;
        }
        if (uly!=null){// 检查uly是否为null，如果不为null则添加到url中
            url=url+"&uly="+URLEncoder.encode(uly, StandardCharsets.UTF_8);
        }
        if(instFamily!=null){// 检查instFamily是否为null，如果不为null则添加到url中
            url=url+"&instFamily="+URLEncoder.encode(instFamily, StandardCharsets.UTF_8);
        }
        if(instId!=null){// 检查instId是否为null，如果不为null则添加到url中
            url=url+"&instId="+URLEncoder.encode(instId, StandardCharsets.UTF_8);
        }
        return url;
    }
}
