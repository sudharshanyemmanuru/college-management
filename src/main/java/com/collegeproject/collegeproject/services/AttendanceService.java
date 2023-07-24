package com.collegeproject.collegeproject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.collegeproject.model.Attendance;
import com.collegeproject.collegeproject.repositories.AttendanceRepository;



@Service
public class AttendanceService {
	@Autowired
	private AttendanceRepository attendanceRepository;
	public boolean saveAttendance(Attendance attendance) {
		return attendanceRepository.saveAttendance(attendance)>0;
	}
	public List<Attendance> filterAttendance(String subId,String rollNum){
		return
				attendanceRepository.getAllAttendanceDetails()
				.stream().filter(attend->attend.getSubId().equals(subId)&&attend.getRno().equals(rollNum))
				.collect(Collectors.toList());
	}
}
