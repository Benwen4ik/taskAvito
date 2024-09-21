package com.tech.linkShort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqids.Sqids;

@SpringBootApplication
public class LinkShortApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkShortApplication.class, args);
	}

	@Bean
	public Sqids createSqids(){
		return Sqids.builder().alphabet("k3G7QAe51FCsPW92uEOyq4Bg6Sp8YzVTmnU0liwDdHXLajZrfxNhobJIRcMvKt").build();
	}

}
