package com.tencent.wxcloudrun.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import java.util.List;

/**
 * 消息体形如： {"id":"7dec8fc4-8a5c-40fc-b68c-9559df2e9aec","object":"chat.completion","created":1740964414,"model":"deepseek-chat","choices":[{"index":0,"message":{"role":"assistant","content":"您好！我是由中国的深度求索（DeepSeek）公司开发的智能助手DeepSeek-V3。如您有任何任何问题，我会尽我所能为您提供帮助。"},"logprobs":null,"finish_reason":"stop"}],"usage":{"prompt_tokens":6,"completion_tokens":37,"total_tokens":43,"prompt_tokens_details":{"cached_tokens":0},"prompt_cache_hit_tokens":0,"prompt_cache_miss_tokens":6},"system_fingerprint":"fp_3a5770e1b4_prod0225"}
 */
@Data
public class DeepSeekResponse {
    
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;

    @Data
    public static class Choice {
        private int index;
        private Message message;
        private Object logprobs;
        private String finishReason;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }

    @Data
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
        private PromptTokensDetails promptTokensDetails;
        private int promptCacheHitTokens;
        private int promptCacheMissTokens;

        @Data
        public static class PromptTokensDetails {
            private int cachedTokens;
        }
    }

    public String getResponseText() {
        try {
            return this.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}