package com.collegeproject.collegeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Faculty;
import com.collegeproject.collegeproject.repositories.FacultyRepository;


@Service
public class FacultyService {
	@Autowired
	private FacultyRepository facultyRepository;
	public boolean saveDetails(Faculty faculty) {
		boolean result=false;
		if(facultyRepository.saveDetails(faculty)>0)
			result=true;
		return result;
	}
	public Faculty authenticateFaculty(String userName,String password) {
		return facultyRepository.getFaculty(userName, password);
	}
}
