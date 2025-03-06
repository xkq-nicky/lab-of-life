package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.utils.DeepSeekClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeepSeekRequest {

    private List<Message> messages; // 消息列表
    private String model; // 模型名称
    private double frequencyPenalty; // 频率惩罚
    private int maxTokens; // 最大 token 数
    private double presencePenalty; // 存在惩罚
    private ResponseFormat responseFormat; // 响应格式
    private Object stop; // 停止条件
    private boolean stream; // 是否流式输出
    private Object streamOptions; // 流式输出选项
    private double temperature; // 温度
    private double topP; // Top-p 采样
    private Object tools; // 工具
    private String toolChoice; // 工具选择
    private boolean logprobs; // 是否返回 logprobs
    private Object topLogprobs; // 返回的 top logprobs

    public void addMessage(Message message) {
        this.getMessages().add(message);
    }

    // 初始化方法，填充默认值
    public static DeepSeekRequest init(String content) {
        DeepSeekRequest request = new DeepSeekRequest();
        request.setMessages(new ArrayList<>());
        request.setModel("deepseek-chat");
        request.setFrequencyPenalty(0);
        request.setMaxTokens(8192);
        request.setPresencePenalty(0);
        request.setResponseFormat(new ResponseFormat());
        request.setStop(null);
        request.setStream(false);
        request.setStreamOptions(null);
        request.setTemperature(1);
        request.setTopP(1);
        request.setTools(null);
        request.setToolChoice("none");
        request.setLogprobs(false);
        request.setTopLogprobs(null);
        request.addMessage(Message.creteUserMessage(content));
        return request;
    }

    // Message 类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role; // 角色
        private String content; // 内容

        public static Message creteUserMessage(String content) {
            return new Message("user", content);
        }
    }

    // ResponseFormat 类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFormat {
        private String type = "text"; // 默认值为 "text"
    }
}