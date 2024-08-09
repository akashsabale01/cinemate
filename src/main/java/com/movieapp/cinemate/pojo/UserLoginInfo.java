package com.movieapp.cinemate.pojo;

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
public class UserLoginInfo {

	@NotNull(message = "email is required")
	@Pattern(regexp = "^[\\w]+@[\\w]+\\.[a-zA-Z]{2,}$", message = "email must be valid") 
	private String email;

	@NotNull(message = "password is required")
	@Pattern(regexp = "^[a-zA-Z0-9@#_]+$", message = "password must be valid")
	private String password;

}
