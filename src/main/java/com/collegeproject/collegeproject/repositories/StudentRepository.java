package com.collegeproject.collegeproject.repositories;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.collegeproject.collegeproject.model.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class StudentRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Student> mapper=(ResultSet rs,int count)->{
		return 
			new Student(rs.getString("name"),rs.getString("department"),rs.getString("programe"),rs.getString("roll_num"),
			rs.getString("home_town"),rs.getString("address"),rs.getString("pin_code"),rs.getString("state"),
			rs.getString("contact_number"),rs.getString("adhar_number"),rs.getString("pan_number"),rs.getString("user_name"),
			rs.getString("password"),rs.getString("batch"),rs.getString("section"));
	};
	
	public int saveDetails(Student student) {
		if(student==null) {
			log.info("student object is null");
			return 0;
		}else {
			String sql="insert into student values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			return jdbcTemplate.update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, student.getName());
					ps.setString(2, student.getDepartment());
					ps.setString(3, student.getProgramme());
					ps.setString(4, student.getRollNum());
					ps.setString(5, student.getHomeTown());
					ps.setString(6, student.getAddress());
					ps.setString(7, student.getZipCode());
					ps.setString(8, student.getState());
					ps.setString(9, student.getContact());
					ps.setString(10, student.getAdharNum());
					ps.setString(11, student.getPanNum());
					ps.setString(12, student.getUserName());
					ps.setString(13, student.getPassword());
					ps.setString(14, student.getBatch());
					ps.setString(15, student.getSection());
				}
			});
		}
	}
	public List<Student> getAllStudents(){
		String sql="select * from student";
		return 
				jdbcTemplate.query(sql, mapper);
	}
	public Student getStudent(String userName, String password) {
		List<Student> students=getAllStudents();
		List<Student> filterStudents=students.stream().filter(s->s.getUserName().equalsIgnoreCase(userName)&&s.getPassword().equalsIgnoreCase(password)).collect(Collectors.toList());
		return (filterStudents.size()>0)?  filterStudents.get(0): null;
	}
	public int deleteByName(String name) {
		String sql="delete from student where name=?";
		return jdbcTemplate.update(sql, name);
	}

}
