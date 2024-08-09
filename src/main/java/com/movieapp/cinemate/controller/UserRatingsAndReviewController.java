package com.movieapp.cinemate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieapp.cinemate.entity.UserRatingsAndReview;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.UserRatingsAndReviewException;
import com.movieapp.cinemate.pojo.ApiResponse;
import com.movieapp.cinemate.pojo.RatingModel;
import com.movieapp.cinemate.pojo.ReviewModel;
import com.movieapp.cinemate.pojo.Trending;
import com.movieapp.cinemate.service.UserRatingsAndReviewService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserRatingsAndReviewController {

	@Autowired
	UserRatingsAndReviewService userRatingsAndReviewService;

	@GetMapping(value = "/rating/{movie_id}")
	public ResponseEntity<RatingModel> getAverageRatingOfMovie(@PathVariable Integer movie_id)
			throws MovieException {
		log.info("Fetching average rating for movie with id: {}", movie_id);
		RatingModel ratingModel = userRatingsAndReviewService.getAverageRatingOfMovie(movie_id);
		log.info("Retrieved average rating for movie with id: {}", movie_id);

		return new ResponseEntity<RatingModel>(ratingModel, HttpStatus.OK);
	}

	@GetMapping(value = "/reviews/{movie_id}")
	public ResponseEntity<List<ReviewModel>> getMovieReviews(@PathVariable Integer movie_id) throws MovieException {
		log.info("Fetching reviews for movie with id: {}", movie_id);
		List<ReviewModel> moviewReviewList = userRatingsAndReviewService.getMovieReviews(movie_id);
		log.info("Retrieved {} reviews for movie with id: {}", moviewReviewList.size(), movie_id);

		return new ResponseEntity<List<ReviewModel>>(moviewReviewList, HttpStatus.OK);
	}

	@GetMapping(value = "/trending-movies")
	public ResponseEntity<List<Trending>> trendingMovies() throws MovieException {
		log.info("Fetching trending movies");
		List<Trending> trendingMoviesList = userRatingsAndReviewService.trendingMovies();
		log.info("Retrieved {} trending movies", trendingMoviesList.size());

		return new ResponseEntity<List<Trending>>(trendingMoviesList, HttpStatus.OK);
	}

	@PostMapping(value = "/rating-and-review")
	public ResponseEntity<ApiResponse> giveRatingAndReviewOfMovie(
			@RequestBody @Valid UserRatingsAndReview userRatingsAndReview) throws UserRatingsAndReviewException {
		log.info("Adding rating and review for movie with id: {}", userRatingsAndReview.getMovieId());
		userRatingsAndReviewService.giveRatingAndReviewOfMovie(userRatingsAndReview);
		log.info("Rating and review added successfully for movie with id: {}", userRatingsAndReview.getMovieId());

		ApiResponse response = new ApiResponse("Rating and Review of given movie added successfully in database", 201);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}
}
