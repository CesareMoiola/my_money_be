package com.cm.my_money_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MyMoneyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMoneyBeApplication.class, args);
	}

}
