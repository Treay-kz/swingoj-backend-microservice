package com.treay.swingojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.treay.swingojbackendjudgeservice.mq.CodeMqInitMain.doInitCodeMq;

/**
 * @author Treay
 */
@SpringBootApplication()
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.treay")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.treay.swingojbackendserviceclient.service"})
public class SwingojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        // 初始化消息队列
        doInitCodeMq();
        SpringApplication.run(SwingojBackendJudgeServiceApplication.class, args);
    }
}
