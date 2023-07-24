package com.collegeproject.collegeproject.controllers;



import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.collegeproject.collegeproject.model.Assignment;
import com.collegeproject.collegeproject.model.AssignmentQuestions;
import com.collegeproject.collegeproject.model.AssignmentSubmission;
import com.collegeproject.collegeproject.model.Attendance;
import com.collegeproject.collegeproject.model.StoragePojo;
import com.collegeproject.collegeproject.model.Student;
import com.collegeproject.collegeproject.model.Subject;
import com.collegeproject.collegeproject.services.AssignmentService;
import com.collegeproject.collegeproject.services.AttendanceService;
import com.collegeproject.collegeproject.services.SubjectService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class StudentController {
	@Autowired
	private StoragePojo storagePojo;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AssignmentService assignmentService;
	private List<Subject> subjects;
	
	@RequestMapping(value="/loadSubjecst",method= {RequestMethod.POST,RequestMethod.GET})
	public String getSubjects(@RequestParam String yearSem,Model model) {
	    Student student=(Student)storagePojo.getObj();
	    subjects=subjectService.filterSubjects(student.getDepartment(), yearSem);
		model.addAttribute("uname",student.getUserName());
		model.addAttribute("subjects",subjects);
		return "studentLoad";
	}
	@RequestMapping("/showAttendance")
	public String showAttendance(@RequestParam String subId,Model model) {
		
		Student student=(Student)storagePojo.getObj();
		List<Attendance> attendances=attendanceService.filterAttendance(subId, student.getRollNum());
		model.addAttribute("uname",student.getUserName());
		model.addAttribute("attendances",attendances);
		List<String> name=subjects.stream().filter(s->s.getSubject_id().equalsIgnoreCase(subId)).map(s->s.getSubject_name()).collect(Collectors.toList());
		model.addAttribute("SubName",name.get(0));
		model.addAttribute("subId",subId);
		model.addAttribute("RollNum",student.getRollNum());
		return "studentAttendance";
	}
	@RequestMapping("/gotodashboard")
	public String gotoDashBoard(Model model) {
		Student student=(Student)storagePojo.getObj();
		model.addAttribute("uname",student.getUserName());
		model.addAttribute("subjects",subjects);
		return "studentLoad";
	}
	@RequestMapping("/loadAssignments")
	public String loadAssignments(@RequestParam String subName,@RequestParam String subId,
			@RequestParam String yearSem,Model model) {
		log.info("SubName : "+subName);
		log.info("subId : "+subId);
		log.info("YearSem : "+yearSem);
		Student student=(Student)storagePojo.getObj();
		log.info("Dept : "+student.getDepartment());
		log.info("Section : "+student.getSection());
		List<Assignment> assignments=assignmentService.filterAssignments(subName, subId, student.getDepartment(), student.getSection(), yearSem);
		log.info("Assignments:"+assignments.toString());
		model.addAttribute("assignments",assignments);
		return "assignmentLoad";
	}
	@RequestMapping(value="/openAssignment",method= {RequestMethod.POST,RequestMethod.GET})
	public String openAssignment(@RequestParam(required=false) Integer assignNum,@RequestParam(required=false) String subjectId,
			@RequestParam(required=false) String subjectName,@RequestParam(required=false) String savedStatus  ,Model model) {
		String saveMsg="";
		String notSaveMsg="";
		if(savedStatus!=null) {
			if(savedStatus.equals("saved"))
				saveMsg="Your Assignment saved suceessfully!!!";
			else if(savedStatus.equals("notsaved"))
				notSaveMsg="You already submitted this assignment!!";
		}
		List<AssignmentQuestions> questions=assignmentService.getQuestions(assignNum, subjectId);
		model.addAttribute("subjectName",subjectName);
		model.addAttribute("Num",assignNum);
		model.addAttribute("subjectId",subjectId);
		model.addAttribute("questions",questions);
		model.addAttribute("savedMessage",saveMsg);
		model.addAttribute("notSavedMessage",notSaveMsg);
		return "assignmentPage";
	}
	@PostMapping("/addSubmission")
	public String addSubmission(@RequestParam Integer assignNumber,@RequestParam String subjectId,@RequestParam String subjectName,HttpServletRequest request) {
		List<String> ans=new ArrayList<>();
		String answer="";
		Student student=(Student)storagePojo.getObj();
		AssignmentSubmission assignmentSubmission=new AssignmentSubmission();
		assignmentSubmission.setAssignNum(assignNumber);
		assignmentSubmission.setRollNum(student.getRollNum());
		assignmentSubmission.setSubId(subjectId);
		assignmentSubmission.setBatch(student.getBatch());
		assignmentSubmission.setSection(student.getSection());
		assignmentSubmission.setDept(student.getDepartment());
		Enumeration<String> answers= request.getParameterNames();
		while(answers.hasMoreElements()) {
			answer=answers.nextElement();
			if(answer.startsWith("ans-"))
				ans.add(request.getParameter(answer));
		}
		assignmentSubmission.setAnswers(ans);
		if(assignmentService.addSubmission(assignmentSubmission))
			return "redirect:/openAssignment?assignNum="+assignNumber+"&subjectId="+subjectId+"&subjectName="+subjectName+"&savedStatus=saved";
		else
			return "redirect:/openAssignment?assignNum="+assignNumber+"&subjectId="+subjectId+"&subjectName="+subjectName+"&savedStatus=notsaved";
	}


}
