package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//全局回滚调用方法
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
@RestController
@Slf4j
public class OrderHystirxController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {

        return paymentHystrixService.paymentInfo_OK(id);
    }

    //实际用时3s，调用paymentTimeOutFallbackMethod方法
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
//    })
//  service实现类兜底
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {

        //controller本身有错调用的是全局fallback.没错的话服务器端宕机调用的是service实现类兜底
        //测试服务器端宕机service实现类兜底需要注掉int age = 10/0;
        //int age = 10/0;
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){

        return "我是消费者80，对方支付系统繁忙请十秒钟再试";
    }

    public String payment_Global_FallbackMethod() {

        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }

}



