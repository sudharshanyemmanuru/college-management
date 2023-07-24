package com.collegeproject.collegeproject.controllers;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.collegeproject.collegeproject.model.Assignment;
import com.collegeproject.collegeproject.model.AssignmentSubmission;
import com.collegeproject.collegeproject.model.Attendance;
import com.collegeproject.collegeproject.model.StoragePojo;
import com.collegeproject.collegeproject.model.Student;
import com.collegeproject.collegeproject.model.Subject;
import com.collegeproject.collegeproject.services.AssignmentService;
import com.collegeproject.collegeproject.services.AttendanceService;
import com.collegeproject.collegeproject.services.StudentService;
import com.collegeproject.collegeproject.services.SubjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FacultyController {
	private List<Subject> subjects;
	private List<Student> students;
	private String subjectName;
	private String subjectId;
	private boolean flag;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private StoragePojo storagePojo;
	@RequestMapping(value = "/load", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadDetails(@RequestParam() String department,
			@RequestParam() String yearSem, @RequestParam() String section,
			@RequestParam() String batch, Model model,HttpSession session) {
		
		
		model.addAttribute("uname",storagePojo.getUserName());
		subjects = subjectService.filterSubjects(department, yearSem);
	    students = studentService.filterStudents(section, batch,department);
	    session.setAttribute("subjects", subjects);
	    session.setAttribute("students", students);
	    session.setAttribute("department", department);
	    session.setAttribute("yearSem", yearSem);
	    session.setAttribute("section", section);
	    session.setAttribute("batch", batch);
		log.info(subjects.toString());
		log.info(students.toString());
		model.addAttribute("subjects", subjects);
		storagePojo.setDept(department);
		storagePojo.setBatch(batch);
		storagePojo.setSection(section);
		storagePojo.setSemNum(yearSem);
		return "facultyLoad";
	}

	@RequestMapping("/takeAttendance")
	public String takeAttendance( @RequestParam String subName,Model model) {
		if(flag==true)
			model.addAttribute("attendStatus","Attendance submitted Successfully");
		model.addAttribute("uname",storagePojo.getUserName());
		model.addAttribute("students", students);
		subjectName=subName;
		flag=false;
		model.addAttribute("subjectCode",this.getSubjectCodes().get(subName));
		return "attendanceform";
	}
	@RequestMapping("/goBack")
	public String goBack(Model model,HttpSession session) {
		model.addAttribute("uname",storagePojo.getUserName());
		model.addAttribute("subjects", session.getAttribute("subjects"));
		return "facultyLoad";
	}
	private Map<String,String> getSubjectCodes(){
		Map<String,String> subjectCodes=new HashMap<>();
		for(Subject sub:subjects) {
			subjectCodes.put(sub.getSubject_name(), sub.getSubject_id());
		}
		return subjectCodes;
	}
	@PostMapping("/submitAttendance")
	public String submitAttendance(HttpServletRequest request,HttpServletResponse response,Model model) {
		int count=0;
		model.addAttribute("uname",storagePojo.getUserName());
		model.addAttribute("subName",subjectName);
	    Enumeration<String> names= request.getParameterNames();
	    log.info("Roll Num: "+request.getParameter("roll-0"));
	    while(names.hasMoreElements()) {
	    	Attendance attendance = new Attendance();
	    	attendance.setRno(request.getParameter(names.nextElement()));
	    	attendance.setSubId(request.getParameter(names.nextElement()));
	    	attendance.setStatus(request.getParameter(names.nextElement()));
	    	LocalDate currentDate=LocalDate.now();
	    	DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	attendance.setDate(currentDate.format(formatter));
	    	if(attendanceService.saveAttendance(attendance)) {
	    		flag=true;
	    		log.info("Attendance saved successfully");
	    		log.info("sub Name : "+subjectName);
	    	}
	    }
		return "redirect:/takeAttendance?subName="+subjectName;
	}
	@RequestMapping("/upload")
	public String uploadAssignment(@RequestParam String subId,@RequestParam(required=false) String subName, @RequestParam(required = false) String assignmentSaved,Model model,HttpSession session) {
		model.addAttribute("uname",storagePojo.getUserName());
		session.setAttribute("subId", subId);
		session.setAttribute("subName", subName);
		if(assignmentSaved!=null) {
			model.addAttribute("assignmentSaved","Assignment uploaded Successfully!!");
		}
		subjectId=subId;
		return "assignmentupload";
	}
	@PostMapping("/saveAssignment")
	public String saveAssignment(@RequestParam String assignmentNum,@RequestParam String questions,HttpSession session) {
		List<String> qstns= new ArrayList<>();
		String[] arr=questions.split("\n");
		Assignment assignment = new Assignment();
		assignment.setAssignmentNum(Integer.parseInt(assignmentNum));
		assignment.setSubName(session.getAttribute("subName").toString());
		assignment.setSubId(session.getAttribute("subId").toString());
		assignment.setDept(session.getAttribute("department").toString());
		assignment.setSection(session.getAttribute("section").toString());
		assignment.setSemNum(session.getAttribute("yearSem").toString());
		for(String a:arr) {
			a=a.trim();
			qstns.add(a);
		}
		assignment.setQuestions(qstns);
		log.info(assignment.getQuestions().toString());
		assignmentService.saveAssignment(assignment);
		return "redirect:/upload?subId="+session.getAttribute("subId").toString()+"&assignmentSaved=true";
	}
	@RequestMapping("/manageAssignments")
	public String managaeAssignments(@RequestParam String subName,@RequestParam String subId,Model model) {
		String dept=storagePojo.getDept();
		String semNum=storagePojo.getSemNum();
		String section=storagePojo.getSection();
		List<Assignment> assignments=assignmentService.filterAssignments(subName, subId, dept, section, semNum);
		model.addAttribute("assignments",assignments);
		return "manageAssignment";
	}
	@RequestMapping("/deleteAssignment")
	public String deleteAssignment(@RequestParam String subjectName,@RequestParam Integer assignNum,@RequestParam String subjectId) {
		assignmentService.deleteAssignment(assignNum, subjectId, storagePojo.getSection());
		return "redirect:/manageAssignments?subName="+subjectName+"&subId="+subjectId;
	}
	@RequestMapping("/showSubmission")
	public String viewAssignments(@RequestParam String subId,@RequestParam String subName,Model model,HttpSession session) {
		List<AssignmentSubmission> submissions=assignmentService.getSubmissions(subId, session.getAttribute("section").toString(), session.getAttribute("batch").toString());
		@SuppressWarnings("unchecked")
		List<Student> students=(List<Student>) session.getAttribute("students");
		List<Assignment> assignments=assignmentService.filterAssignments(subName, subId, session.getAttribute("department").toString(), session.getAttribute("section").toString(), session.getAttribute("yearSem").toString());
		session.setAttribute("assignments", assignments);
		session.setAttribute("submissions", submissions);
		model.addAttribute("submissions", submissions);
		model.addAttribute("totalAssignments",assignments);
		return "assignmentsSubmission";
	}
	@RequestMapping("/filterAssignments")
	public String filterAssignments(@RequestParam int selectAssignmentNum,Model model,HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Student> students=(List<Student>) session.getAttribute("students");
		@SuppressWarnings("unchecked")
		List<AssignmentSubmission> submissions=(List<AssignmentSubmission>) session.getAttribute("submissions");
		List<AssignmentSubmission> filteredSubmissions=submissions.stream().filter(sub->sub.getAssignNum()==selectAssignmentNum).toList();
		@SuppressWarnings("unchecked")
		List<Assignment> assignments=(List<Assignment>) session.getAttribute("assignments");
		model.addAttribute("submissions",filteredSubmissions);
		model.addAttribute("totalAssignments",assignments);
		model.addAttribute("totalSubmission",filteredSubmissions.size());
		model.addAttribute("totalStrength",students.size());
		return "assignmentsSubmission";	
	}

}
