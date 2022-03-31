package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    //测试手写的轮询算法，先将LoadBalanced注掉
    //@LoadBalanced //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力,使用ribbon做负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
