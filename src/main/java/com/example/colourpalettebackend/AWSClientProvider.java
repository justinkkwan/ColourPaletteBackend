package com.example.colourpalettebackend;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
public class AWSClientProvider {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LambdaClient getLambdaClient() {
        return LambdaClient.create();
    }
}
