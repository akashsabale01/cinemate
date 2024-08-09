package com.movieapp.cinemate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI apiDocConfig() {
		return new OpenAPI().info(new Info().title("Cinemate API")
				.description("Cinemate is Movie Rating and Review platform where users can explore movies, Rate movie, "
						+ "give Review of movie and User Personalized Wishlist of M 	ovie.\n\n"
						+ "The project aims to offer various Analytical operations like trending movies, upcoming movies, released movies, "
						+ "also it offers various CRUD (Create, Read, Update, Delete) operations via API endpoints for movie information, "
						+ "managing user accounts and user review and ratings associated with movies. "
						+ "Cinemate offers advanced search functionalities to narrow down results based on the various criteria, "
						+ "making it easy for users to find specific content."));
	}
}
