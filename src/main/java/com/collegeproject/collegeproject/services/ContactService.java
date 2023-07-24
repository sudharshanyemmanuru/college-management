package com.collegeproject.collegeproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Contact;
import com.collegeproject.collegeproject.repositories.ContactRepository;


@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;
	public boolean saveMessage(Contact contact) {
		boolean result=false;
		if(contactRepository.saveMessage(contact)>0)
			result=true;
		return result;
	}
	public List<Contact> getAll(){
		return contactRepository.getAllContacts();
	}
	public boolean changeStatus(String name) {
		boolean result=false;
		if(contactRepository.changeStatus(name)>0)
			result =true;
		return result;
	}

}
