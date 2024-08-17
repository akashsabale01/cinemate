package com.movieapp.cinemate.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.movieapp.cinemate.entity.User;
import com.movieapp.cinemate.pojo.UpdateUserPojo;
import com.movieapp.cinemate.pojo.UserLoginInfo;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email=?";

		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
	}

	public UserLoginInfo getUserByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM users WHERE email=? AND password=?";

		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<UserLoginInfo>(UserLoginInfo.class), email,
				password);
	}

	public Boolean addUser(User user) throws Exception {
		String sql = "INSERT INTO users(email, password, name, mobile_no, age,role) VALUES (?,?,?,?,?,?)";

		int result = jdbcTemplate.update(sql, new Object[] { user.getEmail(), user.getPassword(), user.getName(),
				user.getMobileNo(), user.getAge(),user.getRole() });

		return (result > 0) ? true : false;
	}

	public User updateUserDetails(String email, UpdateUserPojo user) {
		// update user details
		String sql = "UPDATE users SET password=?, name=?, mobile_no=?, age=? WHERE email=?";

		int result = jdbcTemplate.update(sql,
				new Object[] { user.getPassword(), user.getName(), user.getMobileNo(), user.getAge(), email });

		// return updated user object
		if (result == 0)
			return null;

		User updatedUserDetails = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?",
				new BeanPropertyRowMapper<User>(User.class), email);

		return updatedUserDetails;
	}

	public UserLoginInfo updatePassword(String email, String new_password) {
		// update user password
		String sql = "UPDATE users SET password=? WHERE email=?";

		int result = jdbcTemplate.update(sql, new Object[] { new_password, email });

		// return updated user object
		if (result == 0)
			return null;

		UserLoginInfo user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?",
				new BeanPropertyRowMapper<UserLoginInfo>(UserLoginInfo.class), email);

		return user;
	}

	public Boolean removeUserByEmail(String email) {
		String sql = "DELETE FROM users WHERE email=?";

		int isUserRemoved = jdbcTemplate.update(sql, email);

		return (isUserRemoved == 1) ? true : false;
	}

}
