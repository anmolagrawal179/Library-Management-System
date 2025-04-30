package com.lms.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookResponse {

	private Long id;
	
	@NotBlank
	private String title;
	
	private UserResponse borrowedBy;
}
