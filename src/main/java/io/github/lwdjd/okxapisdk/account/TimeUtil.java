package io.github.lwdjd.okxapisdk.account;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static io.github.lwdjd.okxapisdk.getdata.Network.get;
import static io.github.lwdjd.okxapisdk.parsingjson.Sundry.parsingCurrentTimestamp;


public class TimeUtil {

    /**
     * 获取当前时间戳，格式为unix时间戳。
     *
     * @return 当前时间戳的字符串表示。
     */
    public static String getCurrentTimestamp() throws Exception {
        String httpProxy = "127.0.0.1:10809";
        String timeStamp = null;
        try {
            timeStamp = get("https://www.okx.com/api/v5/public/time",httpProxy);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取时间戳失败");
        }
        return parsingCurrentTimestamp(timeStamp);
    }

    public static String getCurrentIsoTimestamp() {
        // 获取当前时间的Instant对象
        Instant now = Instant.now();

        // 使用DateTimeFormatter来格式化时间戳为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));
        // 格式化时间戳并返回
        return formatter.format(now);
    }

}