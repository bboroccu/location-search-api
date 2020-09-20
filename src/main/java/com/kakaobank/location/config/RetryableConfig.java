package com.kakaobank.location.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableRetry
@Configuration
public class RetryableConfig {
    @Bean
    public List<RetryListener> retryListeners() {
        List<RetryListener> retryListeners = new ArrayList<>();
        RetryListener retryLoggingListener = new RetryListenerSupport() {
            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                super.onError(context, callback, throwable);
                log.warn("[Retry onError] - {}, retryContext: {}, throwable message:{}", context.getAttribute(RetryContext.NAME), context, throwable.getMessage());
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                super.close(context, callback, throwable);
                if (throwable != null) {
                    log.error("[Retry onClose] context: {}, throwable message: {}", context, throwable.getMessage(), throwable);
                }
            }
        };
        retryListeners.add(retryLoggingListener);
        return retryListeners;
    }
}
