package com.luck.dataWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = "com.luck")
public class LuckComeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckComeApplication.class, args);
	}

}
