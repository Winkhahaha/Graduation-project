package org.mineok;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/02/ 14:39
 * @Description
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("org.mineok.dao")
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
}
