package com.collegeproject.collegeproject.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
	private String subId;
	private int assignmentNum;
	private String subName;
	private String dept;
	private String section;
	private String semNum;
	private List<String> questions;

}
