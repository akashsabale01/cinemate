package com.movieapp.cinemate.entity;

import java.time.LocalDate;

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
public class Movie {

	@Positive(message = "id must be positive")
	@NotNull(message = "id is required")
	private Integer id;

	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "title must be a string")
//	@Pattern(regexp = "^[\\w ]+$", message = "title must be a string") 
	@NotNull(message = "title is required")
	private String title;

	@NotNull(message = "category is required")
	private String category;

	@NotNull(message = "Release Date is required")
	private LocalDate releaseDate;

//	private Date releaseDate;
}
