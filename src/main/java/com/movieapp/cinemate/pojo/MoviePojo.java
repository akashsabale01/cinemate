package com.movieapp.cinemate.pojo;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class MoviePojo {

	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "name must be a string")
	@NotNull(message = "title is required")
	private String title;

	@NotNull(message = "category is required")
	private String category;

	@JsonFormat(pattern = "yyyy-MM-dd") 
	@NotNull(message = "Release Date is required")
	private LocalDate releaseDate;

}