package io.github.lwdjd.okxapisdk;




import java.util.List;


import static io.github.lwdjd.okxapisdk.getdata.PublicData.*;


public class Test {
    public static void main(String[] args){
        httpProxy = "127.0.0.1:10809";
        System.out.println("开始测试");
        try {
            List<List<String>> markPriceC = getMarkPriceCandles("BTC-USD-SWAP", null, null, null,null);
            for(List<String> l:markPriceC){
                for (String ls:l){
                    System.out.print(ls);
                    System.out.print(" ");
                }
                System.out.println();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据获取错误！！！");// 处理异常
        }

    }
}