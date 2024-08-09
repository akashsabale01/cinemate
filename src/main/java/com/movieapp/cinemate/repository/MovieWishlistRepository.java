package com.movieapp.cinemate.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.movieapp.cinemate.entity.Movie;
import com.movieapp.cinemate.entity.User;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.MovieWishlistException;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.MovieWishlistPojo;
import com.movieapp.cinemate.service.MovieService;
import com.movieapp.cinemate.service.UserService;

@Repository
public class MovieWishlistRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	UserService userService;

	@Autowired
	MovieService movieService;

	public void addToMovieWishlist(String email, Integer movieId)
			throws MovieWishlistException, UserException, MovieException {

		// if email not valid throw UserException
		userService.getUserByEmail(email);

		// if movie id not valid throw MovieException
		movieService.getMovieById(movieId);

		// Check if movie was already added in db or not
		List<MovieWishlistPojo> movieWishlistPojo = jdbcTemplate.query(
				"SELECT * FROM movie_wishlist where email=? and movie_id=?",
				new BeanPropertyRowMapper<MovieWishlistPojo>(MovieWishlistPojo.class), email, movieId);

		System.out.println("ddfs " + movieWishlistPojo);

		if (movieWishlistPojo.size() != 0) {
			throw new MovieWishlistException("Movie of given id is already added in wishlist.");
		}

		// email & movie id not present i.e. add new entry

		jdbcTemplate.update("Insert Into movie_wishlist(email, movie_id) Values (?,?)", email, movieId);

	}

	public MovieWishlistPojo getUserMovieWishlist(String email) throws UserException {

		// if email not valid throw UserException
		User user = userService.getUserByEmail(email);

		String sql = "select m.title from movies m " + "inner join movie_wishlist mw " + "on m.id = mw.movie_id "
				+ "where mw.email=?";

		List<String> movies = jdbcTemplate.queryForList(sql, String.class, user.getEmail());
		
		return new MovieWishlistPojo(email, movies);
	}

	public Boolean deleteMovieFromWishlist(String email, Integer movieId) throws UserException, MovieException {

		int isMovieRemoved = 0;

		// if email not valid throw UserException
		User user = userService.getUserByEmail(email);

		// if movie id not valid throw MovieException
		Movie movie = movieService.getMovieById(movieId);

		String sql = "Delete From movie_wishlist where email=? and movie_id=?";

		isMovieRemoved = jdbcTemplate.update(sql, user.getEmail(), movie.getId());
		
		System.out.println("isMovieRemoved -> " + isMovieRemoved);

		return (isMovieRemoved == 1) ? true : false;
	}

}
