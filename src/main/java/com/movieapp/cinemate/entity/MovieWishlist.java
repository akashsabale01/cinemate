package com.movieapp.cinemate.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieWishlist {

	@Positive(message = "id must be positive")
	@NotNull(message = "id is required")
	private Integer id;

	@NotNull(message = "email is required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email must be valid")
	private String email;

	@Positive(message = "id must be positive")
	@NotNull(message = "id is required")
	private Integer movieId;

}
