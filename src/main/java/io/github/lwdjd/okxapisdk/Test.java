package io.github.lwdjd.okxapisdk;




import java.util.List;
import java.util.Map;

import static io.github.lwdjd.okxapisdk.getdata.PublicData.*;


public class Test {
    public static void main(String[] args){
        httpProxy = "127.0.0.1:10809";
        System.out.println("开始测试");
        try {
            List<Map<String, Object>> markPrice = getMarkPrice(SWAP, null, null, null);
            System.out.println(markPrice.get(15).get("instId"));

       }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据获取错误！！！");// 处理异常
        }

    }
}