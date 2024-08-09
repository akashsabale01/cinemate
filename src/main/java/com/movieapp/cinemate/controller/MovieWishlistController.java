package com.movieapp.cinemate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.MovieWishlistException;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.ApiResponse;
import com.movieapp.cinemate.pojo.MovieWishlistPojo;
import com.movieapp.cinemate.service.MovieWishlistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/")
public class MovieWishlistController {

	@Autowired
	MovieWishlistService movieWishlistService;

	@PostMapping(value = "movie-wishlist/{email}/{movieid}")
	public ResponseEntity<ApiResponse> addToMovieWishlist(@PathVariable("email") String email,
			@PathVariable("movieid") Integer movieId) throws MovieWishlistException, MovieException, UserException {
		
		log.info("Adding movie with id: {} to wishlist of user: {}", movieId, email);
		movieWishlistService.addToMovieWishlist(email, movieId);
		log.info("Movie added to wishlist successfully");

		ApiResponse response = new ApiResponse("Movie added to wishlist", 201);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "movie-wishlist/{email}")
	public ResponseEntity<MovieWishlistPojo> getUserMovieWishlist(@PathVariable("email") String email)
			throws UserException {
		
		log.info("Fetching movie wishlist of user: {}", email);
		MovieWishlistPojo movieWishlistPojo = movieWishlistService.getUserMovieWishlist(email);
		log.info("Retrieved movie wishlist of user: {}", email);

		return new ResponseEntity<MovieWishlistPojo>(movieWishlistPojo, HttpStatus.OK);
	}

	@DeleteMapping(value = "movie-wishlist/{email}/{movieid}")
	public ResponseEntity<ApiResponse> deleteMovieFromWishlist(@PathVariable("email") String email,
			@PathVariable("movieid") Integer movieId) throws UserException, MovieException, MovieWishlistException {
		
		log.info("Deleting movie with id: {} from wishlist of user: {}", movieId, email);
		movieWishlistService.deleteMovieFromWishlist(email, movieId);
		log.info("Movie deleted from wishlist successfully");

		ApiResponse response = new ApiResponse("Movie deleted from movie wishlist", 200);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

}
