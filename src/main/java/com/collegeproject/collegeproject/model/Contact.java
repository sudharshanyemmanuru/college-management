package com.collegeproject.collegeproject.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	@NotBlank(message="Name cannot be empty")
	private String name;
	@Email(message="provide a valid email")
	private String email;
	private String course;
	@NotBlank(message="please enter your query")
	@Size(min=10)
	private String message;
	private String status;

}
