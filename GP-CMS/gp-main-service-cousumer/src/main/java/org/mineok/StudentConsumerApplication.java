package org.mineok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/04/ 15:51
 * @Description
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class StudentConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerApplication.class, args);
    }
}
