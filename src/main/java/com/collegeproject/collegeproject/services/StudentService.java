package com.collegeproject.collegeproject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Student;
import com.collegeproject.collegeproject.repositories.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	public boolean saveDetails(Student student) {
		if(student==null)
			log.info("address cannot be obtained because student is null");
		else {
		log.info("address : "+student.getAddress());
		return studentRepository.saveDetails(student)>0;
		}
		return false;
	}
	public List<Student> filterStudents(String section,String batch,String dept){
		List<Student> students=studentRepository.getAllStudents();
		return students.stream().filter(st->st.getSection()
				.equalsIgnoreCase(section) && st.getBatch().equals(batch) && st.getDepartment().equalsIgnoreCase(dept))
				.collect(Collectors.toList());
	}
	public Optional<Student> getStudent(String userName,String Password) {
		Student student= studentRepository.getStudent(userName, Password);
		Optional<Student> optionalStudent=Optional.ofNullable(student);
		return optionalStudent;
	}
	

}
