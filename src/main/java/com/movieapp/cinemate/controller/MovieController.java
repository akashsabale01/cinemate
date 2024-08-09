package com.movieapp.cinemate.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieapp.cinemate.entity.Movie;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.pojo.ApiResponse;
import com.movieapp.cinemate.pojo.MoviePojo;
import com.movieapp.cinemate.service.MovieService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping(value = "user/movies")
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(value = "type", required = false) String type)
			throws MovieException {
		
		log.info("Getting all movies of type: {}", type);
		List<Movie> movieList = movieService.getAllMovies(type);
		log.info("Retrieved {} movies", movieList.size());

		return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
	}

	@GetMapping(value = "user/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) throws MovieException {
		
		log.info("Getting movie with id: {}", id);
		Movie movie = movieService.getMovieById(id);
		log.info("Retrieved movie: {}", movie.getTitle());

		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@GetMapping(value = "user/movies-by-category/{category}")
	public ResponseEntity<List<Movie>> getMoviesByCategory(@PathVariable String category) throws MovieException {
		
		log.info("Getting movies in category: {}", category);
		List<Movie> movieListByCategory = movieService.getMoviesByCategory(category);
		log.info("Retrieved {} movies in category: {}", movieListByCategory.size(), category);

		return new ResponseEntity<List<Movie>>(movieListByCategory, HttpStatus.OK);
	}

	@PostMapping(value = "admin/movies")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> addMovie(@RequestBody @Valid MoviePojo movie) throws MovieException {
		
		log.info("Adding movie: {}", movie.getTitle());
		Boolean result = movieService.addMovie(movie);
		log.info("Movie added successfully: {}", movie.getTitle());

		ApiResponse response = new ApiResponse("Movie added successfully in database", 201);

		return (result) ? new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED) : null;
	}

	@PatchMapping(value = "admin/movies/{id}/{releasedate}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Movie> updateReleaseDateOfMovieById(@PathVariable Integer id,
			@PathVariable LocalDate releasedate) throws MovieException {
		
		log.info("Updating release date of movie with id: {}", id);
		Movie movie = movieService.updateReleaseDateOfMovieById(id, releasedate);
		log.info("Updated release date of movie: {}", movie.getTitle());

		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@PutMapping(value = "admin/movies/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Movie> updateMovieDetailsById(@PathVariable Integer id, @RequestBody @Valid MoviePojo movie)
			throws MovieException {
		
		log.info("Updating movie details for id: {}", id);
		Movie updatedMovieDetails = movieService.updateMovieDetailsById(id, movie);
		log.info("Updated movie details for: {}", updatedMovieDetails.getTitle());

		return new ResponseEntity<Movie>(updatedMovieDetails, HttpStatus.OK);
	}

	@DeleteMapping(value = "admin/movies/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> removeMovieById(@PathVariable Integer id) throws MovieException {
		
		log.info("Removing movie with id: {}", id);
		movieService.removeMovieById(id);
		log.info("Removed movie with id: {}", id);

		ApiResponse response = new ApiResponse("Movie deleted successfully from database", 200);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
