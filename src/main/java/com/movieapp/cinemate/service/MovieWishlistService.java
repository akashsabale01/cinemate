package com.movieapp.cinemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.MovieWishlistException;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.MovieWishlistPojo;
import com.movieapp.cinemate.repository.MovieWishlistRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieWishlistService {

	@Autowired
	MovieWishlistRepository movieWishlistRepository;

	public void addToMovieWishlist(String email, Integer movieId)
			throws MovieWishlistException, UserException, MovieException {

		log.info("Received request to add movie with id {} to the wishlist of user {}", movieId, email);

		movieWishlistRepository.addToMovieWishlist(email, movieId);

		log.info("Successfully added movie with id {} to the wishlist of user {}", movieId, email);

	}

	public MovieWishlistPojo getUserMovieWishlist(String email) throws UserException {

		log.info("Received request to get the movie wishlist of user {}", email);

		MovieWishlistPojo movieWishlistPojo = movieWishlistRepository.getUserMovieWishlist(email);

		log.info("Successfully retrieved the movie wishlist of user {}", email);

		return movieWishlistPojo;
	}

	public void deleteMovieFromWishlist(String email, Integer movieId)
			throws UserException, MovieException, MovieWishlistException {

		log.info("Received request to delete movie with id {} from the wishlist of user {}", movieId, email);

		Boolean isMovieDeleted = movieWishlistRepository.deleteMovieFromWishlist(email, movieId);

		if (isMovieDeleted) {
			log.info("Successfully deleted movie with id {} from the wishlist of user {}", movieId, email);
		} else {
			log.warn("Record for given movie id {} & email {} is not present in db", movieId, email);
			throw new MovieWishlistException("Record for given email & movie id is not present in db");
		}
	}

}
