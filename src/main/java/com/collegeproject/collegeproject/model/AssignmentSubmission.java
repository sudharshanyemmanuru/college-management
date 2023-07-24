package com.collegeproject.collegeproject.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmission {
	private int assignNum;
	private String subId;
	private String rollNum;
	private String batch;
	private String section;
	private String dept;
	private List<String> answers;

}
