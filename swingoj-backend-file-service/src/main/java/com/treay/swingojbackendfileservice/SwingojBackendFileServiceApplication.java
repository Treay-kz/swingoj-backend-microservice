package com.treay.swingojbackendfileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.treay")
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.treay.swingojbackendserviceclient.service"})
public class SwingojBackendFileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwingojBackendFileServiceApplication.class, args);
    }

}
