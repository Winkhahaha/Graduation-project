package org.mineok.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/03/ 20:48
 * @Description
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GPGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GPGatewayApplication.class, args);
    }
}
