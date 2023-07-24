package com.collegeproject.collegeproject.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;
@Component
@SessionScope
@Data
public class StoragePojo {
	private String userName;
	private Object obj;
	private String section;
	private String dept;
	private String batch;
	private String semNum;
	
}
