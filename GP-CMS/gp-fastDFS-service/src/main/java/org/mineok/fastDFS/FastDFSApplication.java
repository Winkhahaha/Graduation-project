package org.mineok.fastDFS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 22:45
 * @Description
 */
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class FastDFSApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastDFSApplication.class, args);
    }
}
