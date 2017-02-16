package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * This will start application.
 * @author raghunandangupta
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(
	exclude = { DataSourceAutoConfiguration.class })
public class CabSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabSearchApplication.class, args);
	}

}
