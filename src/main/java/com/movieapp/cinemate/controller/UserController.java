package com.movieapp.cinemate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieapp.cinemate.entity.User;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.ApiResponse;
import com.movieapp.cinemate.pojo.UpdateUserPojo;
import com.movieapp.cinemate.pojo.UserInfo;
import com.movieapp.cinemate.pojo.UserLoginInfo;
import com.movieapp.cinemate.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping(value = "{email}")
	public ResponseEntity<UserInfo> getUserByEmail(@PathVariable String email) throws UserException {
		
		log.info("Fetching user with email: {}", email);
		User user = userService.getUserByEmail(email);
		log.info("Retrieved user: {}", user.getName());

		UserInfo userInfo = convertUserToUserInfoClass(user);

		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}

	@PutMapping(value = "update-user-details/{email}")
	public ResponseEntity<UserInfo> updateUserDetails(@PathVariable String email,
			@RequestBody @Valid UpdateUserPojo user) throws UserException {
	
		log.info("Updating user details for email: {}", email);
		User updatedUserDetails = userService.updateUserDetails(email, user);
		log.info("User details updated for: {}", updatedUserDetails.getEmail());
		
		UserInfo userInfo = convertUserToUserInfoClass(updatedUserDetails);

		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}

	@PatchMapping(value = "update-password")
	public ResponseEntity<ApiResponse> updatePassword(@RequestBody @Valid UserLoginInfo user) throws UserException {
		
		log.info("Updating password for user: {}", user.getEmail());
		userService.updatePassword(user);
		log.info("Password updated for user: {}", user.getEmail());

		ApiResponse response = new ApiResponse("Password updated successfully", 200);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "{email}")
	public ResponseEntity<ApiResponse> removeUserByEmail(@PathVariable String email) throws UserException {
		
		log.info("Deleting user with email: {}", email);
		userService.removeUserByEmail(email);
		log.info("User deleted: {}", email);

		ApiResponse response = new ApiResponse("User deleted successfully from database", 200);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	private UserInfo convertUserToUserInfoClass(User user) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(user.getEmail());
		userInfo.setName(user.getName());
		userInfo.setMobileNo(user.getMobileNo());
		userInfo.setAge(user.getAge());
		
		return userInfo;
	}


}
