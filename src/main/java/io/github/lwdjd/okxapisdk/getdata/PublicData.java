package io.github.lwdjd.okxapisdk.getdata;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PublicData {
    public final static String MARGIN = "MARGIN";//币币杠杆
    public final static String SWAP = "SWAP";//永续合约
    public final static String FUTURES = "FUTURES";//交割合约
    public final static String OPTION = "OPTION";//期权
    public static String mainUrl = "https://www.okx.com";
    public static String markPriceurlUrl = "/api/v5/public/mark-price";

    /**
     * 获取指定合约的标记价格
     *
     * @param instType   产品类型:
     *                       MARGIN：币币杠杆
     *                       SWAP：永续合约
     *                       FUTURES：交割合约
     *                      OPTION：期权
     * @param uly        标的指数:适用于交割/永续/期权
     * @param instFamily 交易品种:适用于交割/永续/期权
     * @param instId     产品ID，如 BTC-USD-SWAP
     * @return 返回指定合约的标记价格，此处为示例方法，实际返回值为null
     */
    public static String getMarkPrice(String instType, String uly, String instFamily, String instId) throws Exception {
        String url = getMarkPriceBody(instType,uly,instFamily,instId);
        return Network.getWithProxy(url,"127.0.0.1:10808");
    }
    public static String getMarkPriceBody(String instType, String uly, String instFamily, String instId){
        String url ="";
        if (instType!=null){
            url=mainUrl+markPriceurlUrl+
                "?"+
                "instType="+URLEncoder.encode(instType, StandardCharsets.UTF_8);
        }else{
            return null;
        }
        if (uly!=null){
            url=url+"uly="+URLEncoder.encode(uly, StandardCharsets.UTF_8);
        }if(instFamily!=null){
            url=url+"instFamily="+URLEncoder.encode(instFamily, StandardCharsets.UTF_8);
        }if(instId!=null){
            url=url+"instId="+URLEncoder.encode(instId, StandardCharsets.UTF_8);
        }
        return url;
    }
}
