package com.movieapp.cinemate.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.movieapp.cinemate.entity.UserRatingsAndReview;
import com.movieapp.cinemate.exception.MovieException;
import com.movieapp.cinemate.exception.UserRatingsAndReviewException;
import com.movieapp.cinemate.mapper.TrendingRowMapper;
import com.movieapp.cinemate.pojo.RatingModel;
import com.movieapp.cinemate.pojo.ReviewModel;
import com.movieapp.cinemate.pojo.Trending;
import com.movieapp.cinemate.service.MovieService;

@Repository
public class UserRatingsAndReviewRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	MovieService movieService;

	public RatingModel getAverageRatingOfMovie(Integer movie_id) throws MovieException {

		String sql = "SELECT urr.movie_id as movieId, m.title, AVG(urr.rating) as rating "
				+ "FROM user_movie_ratings_review urr " + "inner join movies m " + "on urr.movie_id = m.id "
				+ "WHERE movie_id=?";

		movieService.getMovieById(movie_id); // movie id validation

		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<RatingModel>(RatingModel.class), movie_id);
	}

	public List<ReviewModel> getMovieReviews(Integer movie_id) throws MovieException {

		String sql = "SELECT urr.email, m.title, urr.review " + "FROM user_movie_ratings_review urr "
				+ "inner join movies m " + "on urr.movie_id = m.id " + "WHERE movie_id=?";

		movieService.getMovieById(movie_id); // movie id validation

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ReviewModel>(ReviewModel.class), movie_id);
	}

	public List<Trending> trendingMovies() {

		String sql = "select m.title, TRUNCATE(avg(r.rating),0) as avgRating "
				+ " from user_movie_ratings_review r inner join movies m on r.movie_id = m.id "
				+ " group by r.movie_id order by avgRating desc limit 3";

		List<Trending> list = jdbcTemplate.query(sql, new TrendingRowMapper());

		return list;
	}

	public Boolean giveRatingAndReviewOfMovie(UserRatingsAndReview userRatingsAndReview)
			throws UserRatingsAndReviewException {

		// Check if same data already exits or not
		String countSql = "SELECT count(*) FROM user_movie_ratings_review WHERE email=? and movie_id=?";

		Integer noOfOccurencesOfGivenData = jdbcTemplate.queryForObject(countSql, Integer.class,
				userRatingsAndReview.getEmail(), userRatingsAndReview.getMovieId());

		if (noOfOccurencesOfGivenData != 0) {
			throw new UserRatingsAndReviewException("Data already added in database");
		}
		
		// insert new data

		String sql = "INSERT INTO user_movie_ratings_review(email, movie_id, rating, review) VALUES (?,?,?,?)";

		int result = 0;

		try {
			result = jdbcTemplate.update(sql,
					new Object[] { userRatingsAndReview.getEmail(), userRatingsAndReview.getMovieId(),
							userRatingsAndReview.getRating(), userRatingsAndReview.getReview() });
		} catch (Exception e) {
			if (e.getMessage().contains("movies")) {
				throw new UserRatingsAndReviewException("Movie Id not found, Enter valid movie id");
			} else if (e.getMessage().contains("users")) {
				throw new UserRatingsAndReviewException("User of given email not found, Enter valid email");
			}
		}

		return (result > 0) ? true : false;
	}

}
