package com.cm.my_money_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class MyMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMoneyApplication.class, args);
	}

}
