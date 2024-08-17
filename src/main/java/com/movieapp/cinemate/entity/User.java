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
public class User {

	@NotNull(message = "email is required")
//	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email must be valid")
	@Pattern(regexp = "^[\\w]+@[\\w]+\\.[a-zA-Z]{2,}$", message = "email must be valid") 
	private String email;

	@NotNull(message = "password is required")
	@Pattern(regexp = "^[a-zA-Z0-9@#]+$", message = "password must be valid")
	private String password;

	@NotNull(message = "name is required")
	@Pattern(regexp = "^[a-zA-Z_ ]+$", message = "name must be valid")
	private String name;

	@Pattern(regexp = "\\d{10}", message = "mobile number must be atleast 10 digit")
	@NotNull(message = "mobile number is required")
	private String mobileNo;

	@Positive(message = "age must be positive")
	@NotNull(message = "age is required")
	@Max(value = 100, message = "Maximum age is 100")
	@Min(value = 18, message = "Minimum age is 18")
	private Integer age;

	@NotNull(message = "role is required")
	private String role;

//	@NotNull(message = "username is required")
//	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "username must be valid")
//	private String username;

}