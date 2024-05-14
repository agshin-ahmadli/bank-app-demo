package com.example.bankinkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class BankinkAppApplication {

	public static void main(String[] args) {
		String date = LocalDate.now().toString();
		System.out.println(date.replace("-", ""));
		SpringApplication.run(BankinkAppApplication.class, args);
	}

}
