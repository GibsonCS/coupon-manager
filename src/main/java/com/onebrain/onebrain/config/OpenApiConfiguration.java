package com.onebrain.onebrain.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Coupon Manager API",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Gibson Cruz",
                        url = "https://github.com/GibsonCS/coupon-manager/"
                ),
                summary = "API for discount coupon manager"
        )
)
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
			.addServersItem(new Server().url("https://cm.gtech.dev.br"));
	}
}
