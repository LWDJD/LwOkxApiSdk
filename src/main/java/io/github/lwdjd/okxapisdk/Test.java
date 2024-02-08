package io.github.lwdjd.okxapisdk;

import static io.github.lwdjd.okxapisdk.getdata.PublicData.*;

public class Test {
    public static void main(String[] args){
        System.out.println("开始测试");
        try {
            String markPrice = getMarkPrice(MARGIN, null, null, null);
            System.out.println("markPrice:" + markPrice);
        }
        catch (Exception e) {
            System.out.println("数据获取错误！！！");// 处理异常
        }
    }
}