package com.example.chatting_websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.backoff.FixedBackOffPolicy;

@Configuration
public class RetryConfig {

    @Bean
    public SimpleRetryPolicy retryPolicy() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);  // 최대 재시도 횟수 3
        return retryPolicy;
    }
    @Bean
    public RetryTemplate retryTemplate(SimpleRetryPolicy retryPolicy) {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 재시도 간격 설정 (10000ms 대기 후 재시도)
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(10000);  // 10초 대기

        retryTemplate.setRetryPolicy(retryPolicy);  // SimpleRetryPolicy 설정
        retryTemplate.setBackOffPolicy(backOffPolicy);  // 재시도 간격 설정

        return retryTemplate;
    }
}
