package com.collegeproject.collegeproject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Subject;
import com.collegeproject.collegeproject.repositories.SubjectRepository;


@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	public List<Subject> filterSubjects(String dept,String yearSem){
		List<Subject> subjects=subjectRepository.getAllSubjects();
		List<Subject> filteredSubjects=subjects.stream()
				.filter(s->s.getDept().equals(dept)&&s.getYear_sem().equals(yearSem))
				.collect(Collectors.toList());
		return filteredSubjects;
	}

}
