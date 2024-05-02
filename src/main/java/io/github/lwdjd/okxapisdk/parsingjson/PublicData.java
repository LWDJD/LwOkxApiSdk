package io.github.lwdjd.okxapisdk.parsingjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PublicData {
    /**
    * 解析标记价格K线数据的JSON字符串，提取需要的数据
    *
    * @param jsonString JSON字符串
    * @return 包含所需数据的列表
    */
    public static List<Map<String, Object>> parsingMarkPrice(String jsonString) throws Exception {
        // 将JSON字符串转换为JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 获取code字段的值
        String code = jsonObject.getString("code");

        //错误码不是0则抛出异常信息
        if(!Objects.equals(code, "0")){
            throw new Exception("Error: " + jsonObject.getString("msg") + " code: "+code);
        }

        // 获取data字段，它是一个JSON数组
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // 将data数组中的每个元素转换为Map<String, Object>
        return JSON.parseObject(dataArray.toJSONString(), new TypeReference<>() {
        });
    }

    /**
     * 解析标记价格JSON字符串，提取需要的数据
     *
     * @param jsonString JSON字符串
     * @return 包含所需数据的列表
     */
    public static List<List<String>> parsingMarkPriceCandles(String jsonString) throws Exception {
        // 将JSON字符串转换为JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 获取code字段的值
        String code = jsonObject.getString("code");

        //错误码不是0则抛出异常信息
        if(!Objects.equals(code, "0")){
            throw new Exception("Error: " + jsonObject.getString("msg") + " code: "+code);
        }


        // 获取data字段，它是一个JSON数组
        JSONArray dataArray = jsonObject.getJSONArray("data");
        // 创建一个新列表，用于存储转换后的数据
        List<List<String>> listOfLists = new ArrayList<>();

        // 遍历 JSON 数组
        for (int i = 0; i < dataArray.size(); i++) {
            // 获取内部数组
            JSONArray innerArray = dataArray.getJSONArray(i);
            // 将内部的 JSONArray 转换为 Java List
            List<String> list = innerArray.toJavaList(String.class);
            // 将转换后的 List 添加到新的列表中
            listOfLists.add(list);
        }
        return listOfLists;
    }
}
