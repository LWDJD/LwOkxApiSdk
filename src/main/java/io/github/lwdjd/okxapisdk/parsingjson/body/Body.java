package io.github.lwdjd.okxapisdk.parsingjson.body;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;


/**
 *  负责将发送的数据进行封装
 *  封装为StringJson类型
 *
 *  @author lwdjd
 */
public class Body {
    private final String data;
    private final JSONObject jsondata;

    /**
     * 负责把StringJson转换为JsonObject
     *
     * @param data 原始数据
     */
    public Body(String data){
        this.data = data;
        this.jsondata = JSONObject.parseObject(data);
    }

    /**
     * 负责把JsonObject转换为StringJson
     *
     * @param data 原始数据
     */
    public Body(JSONObject data){
        jsondata = data;
        this.data = JSON.toJSONString(data);
    }

    /**
     * 负责获取String类型的Body数据
     *
     * @return 返回数据
     */
    public String getStringData(){
        return data;
    }

    /**
     * 负责获取JSONObject类型的Body数据
     *
     * @return 返回数据
     */
    public JSONObject getJSONObject() {
        return jsondata;
    }
}
