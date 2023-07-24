package com.collegeproject.collegeproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.collegeproject.collegeproject.model.Contact;
import com.collegeproject.collegeproject.model.Faculty;
import com.collegeproject.collegeproject.model.StoragePojo;
import com.collegeproject.collegeproject.model.Student;
import com.collegeproject.collegeproject.services.ContactService;
import com.collegeproject.collegeproject.services.FacultyService;
import com.collegeproject.collegeproject.services.StudentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminController {
	
	@Autowired
	private ContactService contactService;
	@Autowired
	private StoragePojo storagePojo;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private StudentService studentService;
	
	
	@GetMapping("/showMessages")
	public String displayMessages(Model model) {
		List<Contact> contacts=contactService.getAll();
		model.addAttribute("contacts",contacts);
		return "messages";
	}
	@GetMapping("/changeStatus")
	public String toggleStatus(@RequestParam String name) {
		contactService.changeStatus(name);
		return "redirect:/showMessages";
	}
	@RequestMapping("/gotoDashboard")
	public String func(Model model) {
		model.addAttribute("uname",storagePojo.getUserName());
		return "admin";
	}
	@RequestMapping(value="/joinFaculty", method= {RequestMethod.GET,RequestMethod.POST})
	public String joinFaculty(@RequestParam(required = false) String saved, Model model) {
		String status="";
		if(saved!=null) {
			status="Saved Successfully!!";
		}
		model.addAttribute("status", status);
		model.addAttribute("faculty",new Faculty());
		return "joinfaculty";
	}
	@PostMapping("/save")
	public String saveDetails( @Valid @ModelAttribute Faculty faculty,Errors error) {
		log.info("Name : "+faculty.getName());
		if(error.hasErrors())
			return "joinfaculty";
		facultyService.saveDetails(faculty);
		return "redirect:/joinFaculty?saved=true";
	}
	@RequestMapping(value="/joinStudent",method= {RequestMethod.GET,RequestMethod.POST})
	public String joinStudent(@RequestParam(required = false) String saved,Model model) {
		String status="";
		if(saved!=null)
			status="details saved successfully!!";
		Student st= new Student();
		model.addAttribute("studentStatus",status);
		model.addAttribute("studentDetails", st);
		return "joinstudent";
	}
	@PostMapping("/saveStudent")
	public String saveStudentDetails( @Valid @ModelAttribute("studentDetails")  Student studentDetails, BindingResult error) {
		if(error.hasErrors()) {
			log.info("Error occured while saving details of student");
			return "joinstudent";
		}
		studentService.saveDetails(studentDetails);
		return "redirect:/joinStudent?saved=true";
	}
}
