package com.treay.swingojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Treay
 */
@SpringBootApplication()
@MapperScan("com.treay.swingojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.treay")
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.treay.swingojbackendserviceclient.service"})
public class SwingojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwingojBackendQuestionServiceApplication.class, args);
    }

}
