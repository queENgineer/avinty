package com.avinty.hr;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SpringBootApplication
public class HrApplication {

	public static void main(String[] args) {

		SpringApplication.run(HrApplication.class, args);
	}
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}
}
