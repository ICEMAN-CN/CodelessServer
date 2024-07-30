package com.codeless.promotion;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Wang DongYang
 */
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan({"cn.hutool.extra.spring", "com.codeless.promotion", "com.google.common"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class PromotionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromotionApplication.class, args);
	}
}