package com.collegeproject.collegeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.collegeproject.collegeproject.model.Contact;
import com.collegeproject.collegeproject.services.ContactService;

import jakarta.validation.Valid;

@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;
	@RequestMapping("/contact")
	public String getContact(Model model) {
		model.addAttribute("contact",new Contact());
		return "contact";
	}
	@PostMapping("/sendMessage")
	public String sendMessage(@Valid @ModelAttribute Contact contact,Errors error) {
		if(error.hasErrors())
			return "contact";
		if(contactService.saveMessage(contact))
			return "redirect:/contact";
		return "contact";
	}

}
