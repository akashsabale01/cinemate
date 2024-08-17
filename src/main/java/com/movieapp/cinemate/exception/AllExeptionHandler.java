package com.movieapp.cinemate.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.movieapp.cinemate.pojo.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class AllExeptionHandler {

	@ExceptionHandler(MovieException.class)
	public ResponseEntity<ApiResponse> handleMovieException(WebRequest request, MovieException ex) {
		log.error("MovieException occurred: {}", ex.getMessage());
		ApiResponse response = new ApiResponse(ex.getMessage(), 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ApiResponse> handleUserException(WebRequest request, UserException ex) {
		log.error("UserException occurred: {}", ex.getMessage());
		if (ex.getMessage().contains("User of given email already present")) {
			ApiResponse response = new ApiResponse(ex.getMessage(), 400);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		if(ex.getMessage().contains("Bad credentials")){
			ApiResponse response = new ApiResponse("User not Logged In, Invalid credentials", 400);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		ApiResponse response = new ApiResponse(ex.getMessage(), 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MovieWishlistException.class)
	public ResponseEntity<ApiResponse> handleMovieWishlistException(WebRequest request, MovieWishlistException ex) {
		log.error("MovieWishlistException occurred: {}", ex.getMessage());
		if (ex.getMessage().contains("Movie of given id not present in movie wishlist.")) {
			ApiResponse response = new ApiResponse(ex.getMessage(), 404);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
		}
		ApiResponse response = new ApiResponse(ex.getMessage(), 400);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserRatingsAndReviewException.class)
	public ResponseEntity<ApiResponse> handleUserRatingsAndReviewException(WebRequest request,
			UserRatingsAndReviewException ex) {
		log.error("UserRatingsAndReviewException occurred: {}", ex.getMessage());
		if (ex.getMessage().contains("Data already added in database")) {
			ApiResponse response = new ApiResponse(ex.getMessage(), 400);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		ApiResponse response = new ApiResponse(ex.getMessage(), 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	// ExceptionHandler for JSON parse error Cannot deserialize value if given data
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleException(HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException occurred: {}", ex.getMessage());
		ApiResponse response = new ApiResponse("JSON parse error, Enter valid data.", 400);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}

	// This ExceptionHandler works if any validations not satisfy 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.error("MethodArgumentNotValidException occurred: {}", ex.getMessage());
		
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		Map<String, String> map = new HashMap<>(errors.size());
		errors.forEach((error) -> {
			String key = ((FieldError) error).getField();
			String val = error.getDefaultMessage();
			map.put(key, val);
		});
		ApiResponse response = new ApiResponse(map.toString(), 400);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleException(Exception ex) {
		log.error("Exception occurred: {}", ex.getMessage());
		
		ApiResponse response = new ApiResponse("Something Went Wrong...", 403);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.FORBIDDEN);
	}
}

