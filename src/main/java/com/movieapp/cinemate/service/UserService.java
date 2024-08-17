package com.movieapp.cinemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieapp.cinemate.entity.User;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.UpdateUserPojo;
import com.movieapp.cinemate.pojo.UserLoginInfo;
import com.movieapp.cinemate.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User getUserByEmail(String email) throws UserException {
		log.info("Fetching user with email: {}", email);
		try {
			User user = userRepository.getUserByEmail(email);
			log.info("Retrieved user: {}", user.getName());

			return user;
		} catch (EmptyResultDataAccessException e) {
			log.error("User of given email not found");
			throw new UserException("User of given email not found");
		}
	}

	public Boolean verify(UserLoginInfo user) throws UserException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserException(e);
        }

        return authentication.isAuthenticated();
    }

	public Boolean userLogin(UserLoginInfo user) throws UserException {
		log.info("Logging in user with email: {}", user.getEmail());
		User savedUserFromDb = getUserByEmail(user.getEmail());

		Boolean isValidUserAndPassword = savedUserFromDb != null
				&& passwordEncoder.matches(user.getPassword(), savedUserFromDb.getPassword());

		if (isValidUserAndPassword) {
			log.info("User logged in successfully: {}", user.getEmail());
		} else {
			log.warn("Invalid credentials for user: {}", user.getEmail());
		}

		return isValidUserAndPassword;
	}

	public void addUser(User user) throws UserException {
		log.info("Registering user with email: {}", user.getEmail());
		Boolean isUserAdded = false;
		try {
			String encodedPassword = passwordEncoder.encode(user.getPassword());

			user.setPassword(encodedPassword);

			isUserAdded = userRepository.addUser(user);
			log.info("User registered successfully: {}", user.getEmail());

		} catch (Exception e) {
			String errorMsg = e.getMessage().contains("Duplicate") ? "User of given email already present"
					: e.getMessage();

			log.error("Error while registering user: {}", errorMsg);
			throw new UserException(errorMsg);
		}

		if (isUserAdded == false) {
			log.error("User not added");
			throw new UserException("User not added");
		}
	}

	public User updateUserDetails(String email, UpdateUserPojo user) throws UserException {
		log.info("Updating user details for email: {}", email);
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword); // override given password with encrypted password

		User updatedUserDetails = userRepository.updateUserDetails(email, user);

		if (updatedUserDetails == null) {
			log.error("There is no user of given email");
			throw new UserException("There is no user of given email");
		}

		log.info("User details updated for: {}", updatedUserDetails.getEmail());
		return updatedUserDetails;
	}

	public void updatePassword(UserLoginInfo user) throws UserException {
		log.info("Updating password for user: {}", user.getEmail());
		String encodedPassword = passwordEncoder.encode(user.getPassword());
	
		user.setPassword(encodedPassword);

		UserLoginInfo updatedUser = userRepository.updatePassword(user.getEmail(), user.getPassword());

		if (updatedUser == null) {
			log.error("There is no user of given email");
			throw new UserException("There is no user of given email");
		}

		log.info("Password updated for user: {}", user.getEmail());
	}

	public void removeUserByEmail(String email) throws UserException {
		log.info("Deleting user with email: {}", email);
		Boolean isUserRemoved = userRepository.removeUserByEmail(email);

		if (isUserRemoved == false) {
			log.error("There is no user of given email");
			throw new UserException("There is no user of given email");
		}

		log.info("User deleted: {}", email);
	}


}
