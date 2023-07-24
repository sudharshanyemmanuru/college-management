package com.collegeproject.collegeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collegeproject.collegeproject.model.UserProfile;
import com.collegeproject.collegeproject.repositories.UserProfileRepository;


@RestController
@RequestMapping("/profile")
public class UserProfileController {
	@Autowired
	private UserProfileRepository userProfileRepository;
	@PutMapping("/updateprofile")
	public ResponseEntity<String> updateUserProfile(@RequestParam int id,@RequestBody UserProfile profile) {
		if(userProfileRepository.updateProfile(profile.getBio(), id)>0)
			return ResponseEntity.status(HttpStatus.OK).body("Updated");
		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Not updated");
	
	}
	

}
