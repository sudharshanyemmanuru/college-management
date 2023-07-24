package com.collegeproject.collegeproject.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
	private int fid;
	@NotBlank(message="Name is rquired")
	private String name;
	@NotBlank(message="department is required")
	private String department;
	@NotBlank(message="Enter the college Name")
	private String nameOfPreviousClg;
	@NotBlank(message="Enter the addres of collge")
	private String addressOfPreviousClg;
	@NotBlank(message="Enter home town")
	private String homeTown;
	@NotBlank(message="Enter address")
	private String address;
	@NotBlank(message="Enter the Zip code")
	private String zipCode;
	@NotBlank(message="Enter the state")
	private String state;
	@NotBlank(message="Enter the contact")
	private String contactNumber;
	@NotBlank(message="Enter the adhar Number")
	private String adharNum;
	@NotBlank(message="Enter the Pan Number")
	private String panNum;
	@NotBlank(message="Enter the user Name")
	private String userName;
	@NotBlank(message="Enter the password")
	private String password;
	
}
