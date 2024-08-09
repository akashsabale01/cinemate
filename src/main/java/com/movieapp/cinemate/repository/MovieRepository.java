package com.movieapp.cinemate.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.movieapp.cinemate.entity.Movie;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.pojo.MoviePojo;

@Repository
public class MovieRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Movie> getAllMovies(String type) throws MovieException {
		String sql = null;

		if (type == null)
			sql = "SELECT * FROM movies";
		else if (type.equalsIgnoreCase("upcoming"))
			sql = "SELECT * FROM movies WHERE release_date > CURDATE() ORDER BY release_date";
		else if (type.equalsIgnoreCase("released"))
			sql = "SELECT * FROM movies WHERE release_date <= CURDATE() ORDER BY release_date";
		else
			throw new MovieException("Invalid type, give either upcoming or released as type");

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
	}

	public Movie getMovieById(Integer id) {
		String sql = "SELECT * FROM movies WHERE id=?";

		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Movie>(Movie.class), id);
	}

	public List<Movie> getMoviesByCategory(String category) {
		String sql = "SELECT * FROM movies WHERE category=?";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class), category);
	}

	public Boolean addMovie(MoviePojo movie) throws MovieException {
		// Check if movie already exits in table
		List<String> titleInDb = jdbcTemplate.queryForList("select title from movies where title= ?", String.class,
				movie.getTitle());

		if (!titleInDb.isEmpty() && titleInDb.get(0).equalsIgnoreCase(movie.getTitle())) {
			throw new MovieException("Movie already added in database");
		}

		String sql = "INSERT INTO movies(title, category, release_date) VALUES (?,?,?)";

		int result = jdbcTemplate.update(sql,
				new Object[] { movie.getTitle(), movie.getCategory(), movie.getReleaseDate() });

		return (result > 0) ? true : false;
	}

	public Movie updateReleaseDateOfMovieById(Integer id, LocalDate releasedate) {
		// update release date
		String sql = "UPDATE movies SET release_date=? WHERE id=?";

		int result = jdbcTemplate.update(sql, new Object[] { releasedate, id });

		// return updated movie object
		if (result == 0)
			return null;

		Movie movie = jdbcTemplate.queryForObject("SELECT * FROM movies WHERE id=?",
				new BeanPropertyRowMapper<Movie>(Movie.class), id);

		return movie;
	}

	public Movie updateMovieDetailsById(Integer id, MoviePojo movie) {
		// update movie details
		String sql = "UPDATE movies SET title=?, category=?, release_date=? WHERE id=?";

		int result = jdbcTemplate.update(sql,
				new Object[] { movie.getTitle(), movie.getCategory(), movie.getReleaseDate(), id });

		// return updated movie object
		if (result == 0)
			return null;

		Movie updatedMovieDetails = jdbcTemplate.queryForObject("SELECT * FROM movies WHERE id=?",
				new BeanPropertyRowMapper<Movie>(Movie.class), id);

		return updatedMovieDetails;
	}

	public List<Movie> getUpcomingMovies() {
		String sql = "SELECT * FROM movies WHERE release_date > CURDATE() ORDER BY release_date";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
	}

	public List<Movie> getReleasedMovies() {
		String sql = "SELECT * FROM movies WHERE release_date <= CURDATE() ORDER BY release_date";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
	}
	
	public void removeMovieById(Integer id) throws MovieException {
		String sql = "DELETE FROM movies WHERE id=?";

		int isMovieRemoved = jdbcTemplate.update(sql, id);
		
		if (isMovieRemoved == 0)
			throw new MovieException("There is no movie of given id");
	}

}
