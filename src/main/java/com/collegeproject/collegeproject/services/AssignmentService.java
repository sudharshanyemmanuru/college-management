package com.collegeproject.collegeproject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Assignment;
import com.collegeproject.collegeproject.model.AssignmentQuestions;
import com.collegeproject.collegeproject.model.AssignmentSubmission;
import com.collegeproject.collegeproject.repositories.AssignmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;
	public boolean saveAssignment(Assignment assignment) {
		return assignmentRepository.saveAssignment(assignment)>0;
	}

	public List<Assignment> filterAssignments(String subName, String subId, String dept, String section,
			String semNum) {
		List<Assignment> assignments = assignmentRepository.getAllAssignments();
		log.info("Service layer : " + assignments.toString());
		return assignments.stream()
				.filter(a -> a.getSubId().equalsIgnoreCase(subId) && a.getDept().equalsIgnoreCase(dept)
						&& a.getSection().equalsIgnoreCase(section) && a.getSemNum().equals(semNum))
				.collect(Collectors.toList());
	}
	public List<AssignmentQuestions> getQuestions(int id,String subId){
		return assignmentRepository.getQuestions(id, subId);
	}
	public boolean deleteAssignment(int assignNum,String subId,String section) {
		return assignmentRepository.deleteAssignment(assignNum, subId, section);
	}
	public boolean addSubmission(AssignmentSubmission assignmentSubmission) {
		return assignmentRepository.storeAssignment(assignmentSubmission);
	}
	public List<AssignmentSubmission> getSubmissions(String subId,String section, String batch){
//		List<AssignmentSubmission> submissions=assignmentRepository.getAllSubmissions();
//		log.info("Submissions : "+submissions.toString());
//		return submissions;
		log.info("Subject Id "+subId+" section : "+section+" batch : "+batch);
     	return assignmentRepository.getAllSubmissions()
				.stream().filter(submission->submission.getSubId().equals(subId) && submission.getSection().equals(section) && submission.getBatch().equals(batch)).toList();
		
	}

}
