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

import com.collegeproject.collegeproject.model.Attendance;

@Repository
public class AttendanceRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Attendance> mapper=(ResultSet rs,int count)->{
		Attendance attendance= new Attendance();
		attendance.setRno(rs.getString("roll_num"));
		attendance.setSubId(rs.getString("subject_id"));
		attendance.setStatus(rs.getString("student_status"));
		attendance.setDate(rs.getString("present_data"));
		return attendance;
	};
	
	public int saveAttendance(Attendance attendance) {
		String sql="insert into attendance values(?,?,?,?)";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, attendance.getRno());
				ps.setString(2, attendance.getSubId());
				ps.setString(3, attendance.getStatus());
				ps.setString(4, attendance.getDate());
			}
		});
	}
	public List<Attendance> getAllAttendanceDetails(){
		String sql="select * from attendance";
		return jdbcTemplate.query(sql, mapper);	
	}

}
