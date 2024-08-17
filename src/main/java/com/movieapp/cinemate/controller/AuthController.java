package com.movieapp.cinemate.controller;

import com.movieapp.cinemate.entity.User;
import com.movieapp.cinemate.exception.UserException;
import com.movieapp.cinemate.pojo.ApiResponse;
import com.movieapp.cinemate.pojo.UserLoginInfo;
import com.movieapp.cinemate.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse> getUserByEmailAndPassword(@RequestBody @Valid UserLoginInfo user)
            throws UserException {

        log.info("Logging in user with email: {}", user.getEmail());
        Boolean isValidUser = userService.verify(user);

        ApiResponse response = null;

        if (!isValidUser) {
            log.warn("Invalid credentials for user: {}", user.getEmail());
            response = new ApiResponse("User not Logged In, Invalid credentials", 401);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
        }

        log.info("User logged in successfully: {}", user.getEmail());
        response = new ApiResponse("User Logged In successfully", 200);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid User user) throws UserException {

        log.info("Registering user with email: {}", user.getEmail());
        userService.addUser(user);
        log.info("User registered successfully: {}", user.getEmail());

        ApiResponse response = new ApiResponse("User added successfully in database", 201);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
    }
}
