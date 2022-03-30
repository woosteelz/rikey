package com.ssafy.rikey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class RikeyApplication {

	public static void main(String[] args) {
//		System.out.println("hiber"+org.hibernate.Version.getVersionString());
		SpringApplication.run(RikeyApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}

	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

	}
}
