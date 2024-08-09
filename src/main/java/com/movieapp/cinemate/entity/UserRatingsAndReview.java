package com.movieapp.cinemate.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UserRatingsAndReview {

	@NotNull(message = "email is required")
//	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email must be valid")
	@Pattern(regexp = "^[\\w]+@[\\w]+\\.[a-zA-Z]{2,}$", message = "email must be valid")
	private String email;

	@Positive(message = "id must be positive")
	@NotNull(message = "id is required")
	private Integer movieId;

	@NotNull(message = "rating is required")
	@Max(value = 10, message = "Maximum rating 10 is allowed")
	@Min(value = 0, message = "Minimum rating 0 is allowed")
	private Integer rating;

	@NotNull(message = "review is required")
	private String review;

}
