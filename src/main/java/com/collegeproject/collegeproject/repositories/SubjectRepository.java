package com.collegeproject.collegeproject.repositories;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.collegeproject.collegeproject.model.Subject;

@Repository
public class SubjectRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Subject> mapper=(ResultSet rs,int count)->{
		return new Subject(rs.getString("subject_name"),rs.getString("subject_id"),rs.getString("dept"),rs.getString("year_sem"));
	};
	public List<Subject> getAllSubjects(){
		String sql="select * from subject";
		return jdbcTemplate.query(sql, mapper);
	}

}
