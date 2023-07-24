package com.collegeproject.collegeproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@NotBlank(message="Please provide student Name")
	private String name;
	@NotBlank(message="please provide department Name")
	private String department;
	@NotBlank(message="please provide programe Name")
	private String programme;
	@NotBlank(message="please enter the Roll Number of Student")
	private String rollNum;
	@NotBlank(message="please Enter the Home town Name of the Student")
	private String homeTown;
	@NotBlank(message="please Enter the address of the student")
	private String address;
	@NotBlank(message="please provide the zip code")
	private String zipCode;
	@NotBlank(message="Please provide the state Name of the student")
	private String state;
	@NotBlank(message="please Enter the contact Number of the student")
	@Pattern(regexp = "[0-9]{10}",message="please enter the valid mobile number")
	private String contact;
	@NotBlank(message="please provide the adhar Number of the student")
	private String adharNum;
	@NotBlank(message="please provide the pan number of the student")
	private String panNum;
	@NotBlank(message="please provide the user name")
	private String userName;
	@NotBlank(message="please provide the password")
	@Pattern(regexp = "[a-zA-Z0-9@#]{6,15}",message="sorry!! your password doesn't meet our requirment!!!")
	private String password;
	@NotBlank(message="please provid the batch details")
	private String batch;
	@NotBlank(message="please Enter the section")
	private String section;

}
