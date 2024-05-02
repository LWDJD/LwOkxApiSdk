package io.github.lwdjd.okxapisdk.parsingjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



import java.util.Objects;

public class Sundry {
    public static String parsingCurrentTimestamp(String jsonString) throws Exception {
        // 将JSON字符串转换为JSONObject
        JSONObject  jsonObject = JSON.parseObject(jsonString);

        // 获取code字段的值
        String code = jsonObject.getString("code");

        //错误码不是0则抛出异常信息
        if(!Objects.equals(code, "0")){
            throw new Exception("Error: " + jsonObject.getString("msg") + " code: "+code);
        }

        // 获取data字段，它是一个JSON数组
        JSONArray dataArray = jsonObject.getJSONArray("data");

        JSONObject dataObject = dataArray.getJSONObject(0);


        return dataObject.getString("ts");
    }
}
