package com.collegeproject.collegeproject.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.collegeproject.collegeproject.model.Faculty;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class FacultyRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Faculty> mapper=(ResultSet rs,int count)->{
		return new Faculty(
				rs.getInt("fid"),rs.getString("name"),rs.getString("department"),rs.getString("nameOfPreviousClg"),
				rs.getString("addressOfPreviousClg"),rs.getString("homeTown"),rs.getString("address"),
				rs.getString("zipCode"),rs.getString("state"),rs.getString("contactNumber"),
				rs.getString("adharNum"),rs.getString("panNum"),rs.getString("userName"),rs.getString("password")
			);
	};
	public int saveDetails(Faculty faculty) {
		String sql="insert into faculty (name,department,nameOfPreviousClg,addressOfPreviousClg,homeTown,address,zipCode,state,contactNumber,adharNum,panNum,userName,password) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, faculty.getName());
				ps.setString(2, faculty.getDepartment());
				ps.setString(3, faculty.getNameOfPreviousClg());
				ps.setString(4, faculty.getAddressOfPreviousClg());
				ps.setString(5, faculty.getHomeTown());
				ps.setString(6, faculty.getAddress());
				ps.setString(7, faculty.getZipCode());
				ps.setString(8, faculty.getState());
				ps.setString(9, faculty.getContactNumber());
				ps.setString(10,faculty.getAdharNum());
				ps.setString(11, faculty.getPanNum());
				ps.setString(12, faculty.getUserName());
				ps.setString(13, faculty.getPassword());
			}
		});
	}
	public Faculty getFaculty(String userName,String password) {
		List<Faculty> faculty;
		String sql="select * from faculty where userName=? and password=?";
		faculty=jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
				ps.setString(2, password);
			}
			
		}, mapper);
		if(faculty.size()>0)
			return faculty.get(0);
		return null;
	}
}
