import io.github.lwdjd.okxapisdk.account.Account;
import io.github.lwdjd.okxapisdk.tx.OrderBookTrading;


import static io.github.lwdjd.okxapisdk.account.TimeUtil.getCurrentTimestamp;
import static io.github.lwdjd.okxapisdk.getdata.PublicData.*;



public class Test {
//    public static void main(String[] args){
//        httpProxy = "127.0.0.1:10809";
//        System.out.println("开始测试");
//        try {
//            List<List<String>> markPriceC = getMarkPriceCandles("BTC-USD-SWAP", null, null, null,null);
//            for(List<String> l:markPriceC){
//                for (String ls:l){
//                    System.out.print(ls);
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }
//
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("数据获取错误！！！");// 处理异常
//        }
//
//    }
    public static void main(String[] args) throws Exception {
        httpProxy = "127.0.0.1:10809";
        System.out.println("开始测试");
        Account t1 = new Account("2e4cc7a4-df81-4f52-a086-692149a8bf03"
                ,"0DDE4A189A59FDE23D77CA318BB1424E"
                ,"Lslm832088."
                ,true
                ,"127.0.0.1:10809");

        String body = "{\n" +
            "    \"instId\":\"ETH-USDC\",\n" +
            "    \"tdMode\":\"cash\",\n" +
            "    \"clOrdId\":\"ttx5\",\n" +
            "    \"side\":\"buy\",\n" +
            "    \"ordType\":\"market\",\n" +
            "    \"px\":\"2.15\",\n" +
            "    \"sz\":\"30\"\n" +
            "}";
        System.out.println(getCurrentTimestamp());
        System.out.println(OrderBookTrading.order(body,t1));
    }

}