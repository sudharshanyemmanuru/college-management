package com.collegeproject.collegeproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
	private String subject_name;
	private String subject_id;
	private String dept;
	private String year_sem;
}
