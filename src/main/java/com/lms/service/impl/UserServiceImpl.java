package com.lms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.lms.entity.User;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.UserRepository;
import com.lms.response.UserResponse;
import com.lms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserResponse addUser(UserResponse userResponse) {

		User user = modelMapper.map(userResponse, User.class);
		User savedUser = userRepository.save(user);

		return modelMapper.map(savedUser, UserResponse.class);
	}

	@Override
	public List<UserResponse> getAllUsers() {

		List<User> users = userRepository.findAll();
		List<UserResponse> userResponses = users.stream().map(user -> modelMapper.map(user, UserResponse.class))
				.collect(Collectors.toList());
		return userResponses;
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public UserResponse updateUser(UserResponse userResponse, Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		user.setName(userResponse.getName());
		user.setEmail(userResponse.getEmail());
		user.setPassword(userResponse.getPassword());

		User savedUser = userRepository.save(user);
		return modelMapper.map(savedUser, UserResponse.class);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		userRepository.delete(user);

	}

}
