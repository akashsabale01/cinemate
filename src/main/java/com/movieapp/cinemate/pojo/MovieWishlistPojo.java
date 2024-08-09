package com.movieapp.cinemate.pojo;

import java.util.List;

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
public class MovieWishlistPojo {

	@NotNull(message = "email is required")
	@Pattern(regexp = "^[\\w]+@[\\w]+\\.[a-zA-Z]{2,}$", message = "email must be valid") 
	private String email;
	
	private List<String> wishlistedMovies;

}
