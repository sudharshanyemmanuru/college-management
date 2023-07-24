package com.collegeproject.collegeproject.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.collegeproject.collegeproject.model.Admin;
import com.collegeproject.collegeproject.model.Faculty;
import com.collegeproject.collegeproject.model.StoragePojo;
import com.collegeproject.collegeproject.model.Student;
import com.collegeproject.collegeproject.repositories.AdminRepository;
import com.collegeproject.collegeproject.services.FacultyService;
import com.collegeproject.collegeproject.services.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private StoragePojo storagePojo;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private StudentService studentService;
	
	
	
	
	@RequestMapping("/login")
	public String getLogin() {
		return "login";
	}
	@PostMapping("/doLogin")
	public String loginUser(@RequestParam String role,@RequestParam String userName,@RequestParam String password,Model model) {
		log.info("ROle : "+role);
		log.info("User Name : "+userName);
		log.info("Password : "+password);
		if(role.equals("Admin")) {
			Admin admin=adminRepository.getAdmin(userName, password);
			if(admin!=null&&userName.equals(admin.getUserName()) && password.equals(admin.getPassword())) {
				model.addAttribute("uname",userName);
				storagePojo.setUserName(userName);
				return "admin";
			}
		}else if(role.equals("Faculty")) {
			log.info("Welcome faculty");
			Faculty f=facultyService.authenticateFaculty(userName, password);
			if(f!=null) {
				model.addAttribute("uname",userName);
				storagePojo.setUserName(userName);
				return "faculty";
			}
		}else if(role.equals("Student")) {
			log.info("Welcome student");
			Optional<Student>student=studentService.getStudent(userName, password);
			if(student.isPresent()) {
				Student s=student.get();
				model.addAttribute("uname",s.getUserName());
				storagePojo.setObj(s);
				return "student";
			}
		}
		return "redirect:/login";
	}

}
