package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ChatRequest;
import com.tencent.wxcloudrun.dto.DeepSeekResponse;
import com.tencent.wxcloudrun.utils.DeepSeekClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepSeekController {

    @PostMapping(value = "/deepseek/chat")
    public ApiResponse chat(@RequestBody ChatRequest request) {
        try {
            DeepSeekResponse response = DeepSeekClient.sendWithTime(request.getMessage());
            String responseText = response.getResponseText();
            return ApiResponse.ok(responseText);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping(value = "/deepseek/chat1")
    public ApiResponse chat1(@RequestBody ChatRequest request) {
        System.out.println(JSON.toJSONString(request));
        return ApiResponse.ok("ok");
    }
}