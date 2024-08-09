package com.movieapp.cinemate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieapp.cinemate.entity.UserRatingsAndReview;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.UserRatingsAndReviewException;
import com.movieapp.cinemate.pojo.RatingModel;
import com.movieapp.cinemate.pojo.ReviewModel;
import com.movieapp.cinemate.pojo.Trending;
import com.movieapp.cinemate.repository.UserRatingsAndReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRatingsAndReviewService {

	@Autowired
	UserRatingsAndReviewRepository userRatingsAndReviewRepository;

	public RatingModel getAverageRatingOfMovie(Integer movie_id) throws MovieException {
		log.info("Fetching average rating for movie with id: {}", movie_id);
		RatingModel ratingModel = userRatingsAndReviewRepository.getAverageRatingOfMovie(movie_id);
		log.info("Retrieved average rating for movie with id: {}", movie_id);

		return ratingModel;
	}

	public List<ReviewModel> getMovieReviews(Integer movie_id) throws MovieException {
		log.info("Fetching reviews for movie with id: {}", movie_id);
		List<ReviewModel> reviewModels = userRatingsAndReviewRepository.getMovieReviews(movie_id);

		if (reviewModels.size() == 0) {
			log.warn("No reviews found for movie with id: {}", movie_id);
			throw new MovieException("reviews for given movie id not found");
		}

		log.info("Retrieved {} reviews for movie with id: {}", reviewModels.size(), movie_id);
		return reviewModels;
	}

	public List<Trending> trendingMovies() throws MovieException {
		log.info("Fetching trending movies");
		List<Trending> trendingMoviesList = userRatingsAndReviewRepository.trendingMovies();

		if (trendingMoviesList.size() == 0) {
			log.warn("No movies are trending right now");
			throw new MovieException("No movies are trending right now");
		}

		log.info("Retrieved {} trending movies", trendingMoviesList.size());
		return trendingMoviesList;
	}

	public void giveRatingAndReviewOfMovie(UserRatingsAndReview userRatingsAndReview) throws UserRatingsAndReviewException {
		log.info("Adding rating and review for movie with id: {}", userRatingsAndReview.getMovieId());
		userRatingsAndReviewRepository.giveRatingAndReviewOfMovie(userRatingsAndReview);
		log.info("Rating and review added successfully for movie with id: {}", userRatingsAndReview.getMovieId());
	}
}
