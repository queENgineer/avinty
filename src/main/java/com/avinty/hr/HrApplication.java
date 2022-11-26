package com.avinty.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SpringBootApplication
public class HrApplication {

	public static void main(String[] args) {

		SpringApplication.run(HrApplication.class, args);
	}

}
