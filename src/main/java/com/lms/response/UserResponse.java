package com.lms.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponse {

	private Long id;

	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;
	

}
