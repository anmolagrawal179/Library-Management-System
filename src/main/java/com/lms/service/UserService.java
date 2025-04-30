package com.lms.service;

import java.util.List;

import com.lms.response.UserResponse;

public interface UserService {

	UserResponse addUser(UserResponse userResponse);

	List<UserResponse> getAllUsers();

	UserResponse getUserById(Long id);

	UserResponse updateUser(UserResponse userResponse, Long id);

	void deleteUser(Long id);

}
