package io.github.lwdjd.okxapisdk.parsingjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;


public class PublicData {
    /**
    * 解析标记价格JSON字符串，提取需要的数据
    * *
    * @param jsonString JSON字符串
    * @return 包含所需数据的列表
    */
    public static List<Map<String, Object>> parsingMarkPrice(String jsonString){
        // 将JSON字符串转换为JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 获取code字段的值
        String code = jsonObject.getString("code");

        // 获取data字段，它是一个JSON数组
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // 将data数组中的每个元素转换为Map<String, Object>
        return JSON.parseObject(dataArray.toJSONString(), new TypeReference<List<Map<String, Object>>>(){});
    }
}
