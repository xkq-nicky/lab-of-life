package com.tencent.wxcloudrun.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.tencent.wxcloudrun.dto.DeepSeekRequest;
import com.tencent.wxcloudrun.dto.DeepSeekResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class DeepSeekClient {

    private static String AUTHORIZATION = "";

    @Value("${deepseek.key}")
    public void setAuthorization(String authorization) {
        AUTHORIZATION = authorization;
    }

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // 连接超时：10 秒
            .readTimeout(60, TimeUnit.SECONDS)    // 读取超时：30 秒
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public static DeepSeekResponse sendWithTime(String message) {
        long startTime = System.currentTimeMillis(); // 记录开始时间
        DeepSeekResponse response = null;
        try {
            response = send(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis(); // 记录结束时间
            long duration = endTime - startTime; // 计算耗时
            System.out.println("方法执行耗时: " + duration + " 毫秒");
        }
        return response;
    }

    public static DeepSeekResponse send(String message) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        DeepSeekRequest deepSeekRequest = DeepSeekRequest.init(message);
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(deepSeekRequest, config));
        Request request = new Request.Builder()
                .url("https://api.deepseek.com/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", AUTHORIZATION)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.body() != null) {
            // 获取 JSON 返回报文
            String jsonResponse = response.body().string();
            System.out.println("JSON Response: " + jsonResponse);
            response.close();
            return JSON.parseObject(jsonResponse, DeepSeekResponse.class);
        } else {
            System.out.println("Request failed: " + response.code());
            response.close();
            return null;
        }
    }
}
