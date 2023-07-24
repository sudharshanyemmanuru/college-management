package com.collegeproject.collegeproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	@NotBlank(message="user name cannot empty")
	private String userName;
	@Pattern(regexp = "[a-zA-Z0-9@#$]{6,12}",message="your password doesn't meet rquirments")
	private String password;

}
