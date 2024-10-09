package com.tech.linkShort;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
//import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqids.Sqids;

import java.util.List;

@SpringBootApplication
public class LinkShortApplication {

	@Value("${server.port}")
	private String PORT_SERVER;

	public static void main(String[] args) {
		SpringApplication.run(LinkShortApplication.class, args);
	}

	@Bean
	public Sqids createSqids(){
		return Sqids.builder().alphabet("k3G7QAe51FCsPW92uEOyq4Bg6Sp8YzVTmnU0liwDdHXLajZrfxNhobJIRcMvKt").build();
	}

	/*@Bean
	public GroupedOpenApi publicUserApi() {
		return GroupedOpenApi.builder()
				.group("Users")
				.pathsToMatch("/users/**")
				.build();
	}

	@Bean
	public OpenAPI customOpenApi(@Value("${application.description}")String appDescription,
								 @Value("${application.version}")String appVersion) {
		return new OpenAPI().info(new Info().title("Link Short")
						.version(appVersion)
						.description(appDescription)
						.license(new License().name("Apache 2.0")
								.url("http://springdoc.org"))
						.contact(new Contact().name("Artem")
								.email("test@gmail.com")))
				.servers(List.of(new Server().url("http://localhost:" + PORT_SERVER)
								.description("Dev service"),
						new Server().url("http://localhost:8082")
								.description("Beta service")));
	}*/

}
