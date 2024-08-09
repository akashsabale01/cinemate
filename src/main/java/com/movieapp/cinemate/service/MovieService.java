package com.movieapp.cinemate.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.movieapp.cinemate.entity.Movie;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.pojo.MoviePojo;
import com.movieapp.cinemate.repository.MovieRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	public List<Movie> getAllMovies(String type) throws MovieException {
		log.info("Fetching all movies of type: {}", type);
		List<Movie> movieList = movieRepository.getAllMovies(type);

		if (movieList.size() == 0) {
			log.warn("No movies found of type: {}", type);
			throw new MovieException("There are no movies available");
		}

		log.info("Retrieved {} movies of type: {}", movieList.size(), type);
		return movieList;
	}

	public Movie getMovieById(Integer id) throws MovieException {
		log.info("Fetching movie with id: {}", id);

		if (id < 0) {
			log.error("Negative movie id not allowed, Movie id must be positive");
			throw new MovieException("Negative movie id not allowed, Movie id must be positive");
		}

		try {
			Movie movie = movieRepository.getMovieById(id);
			log.info("Retrieved movie: {}", movie.getTitle());

			return movie;
		} catch (EmptyResultDataAccessException e) {
			log.error("Movie of given id not found");
			throw new MovieException("Movie of given id not found");
		}
	}

	public List<Movie> getMoviesByCategory(String category) throws MovieException {
		log.info("Fetching movies in category: {}", category);
		List<Movie> movieListByCategory = movieRepository.getMoviesByCategory(category);

		if (movieListByCategory.size() == 0) {
			log.warn("No movies found in category: {}", category);
			throw new MovieException("There are no movies available of given category");
		}

		log.info("Retrieved {} movies in category: {}", movieListByCategory.size(), category);
		return movieListByCategory;
	}

	public Boolean addMovie(MoviePojo movie) throws MovieException {
		log.info("Adding movie: {}", movie.getTitle());
		Boolean isMovieAdded = movieRepository.addMovie(movie);

		if (isMovieAdded == false) {
			log.error("Movie not added");
			throw new MovieException("Movie not added");
		}

		log.info("Movie added successfully: {}", movie.getTitle());
		return isMovieAdded;
	}

	public Movie updateReleaseDateOfMovieById(Integer id, LocalDate releasedate) throws MovieException {
		log.info("Updating release date of movie with id: {}", id);

		if (id < 0) {
			log.error("Negative movie id not allowed, Movie id must be positive");
			throw new MovieException("Negative movie id not allowed, Movie id must be positive");
		}

		Movie movie = movieRepository.updateReleaseDateOfMovieById(id, releasedate);

		if (movie == null) {
			log.error("There is no movie of given id");
			throw new MovieException("There is no movie of given id");
		}

		log.info("Updated release date of movie: {}", movie.getTitle());
		return movie;
	}

	public Movie updateMovieDetailsById(Integer id, MoviePojo movie) throws MovieException {
		log.info("Updating movie details for id: {}", id);

		if (id < 0) {
			log.error("Negative movie id not allowed, Movie id must be positive");
			throw new MovieException("Negative movie id not allowed, Movie id must be positive");
		}

		Movie updatedMovieDetails = movieRepository.updateMovieDetailsById(id, movie);

		if (updatedMovieDetails == null) {
			log.error("There is no movie of given id");
			throw new MovieException("There is no movie of given id");
		}

		log.info("Updated movie details for: {}", updatedMovieDetails.getTitle());
		return updatedMovieDetails;
	}

	public void removeMovieById(Integer id) throws MovieException {
		log.info("Removing movie with id: {}", id);

		if (id < 0) {
			log.error("Negative movie id not allowed, Movie id must be positive");
			throw new MovieException("Negative movie id not allowed, Movie id must be positive");
		}

		movieRepository.removeMovieById(id);
		log.info("Removed movie with id: {}", id);
	}

}
