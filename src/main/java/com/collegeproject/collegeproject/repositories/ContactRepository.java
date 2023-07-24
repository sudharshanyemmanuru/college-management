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

import com.collegeproject.collegeproject.model.Contact;

@Repository
public class ContactRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Contact> contactMapper=(ResultSet rs,int count)->{
		Contact contact= new Contact();
		contact.setName(rs.getString("name"));
		contact.setEmail(rs.getString("email"));
		contact.setCourse(rs.getString("course"));
		contact.setMessage(rs.getString("message"));
		contact.setStatus("open");
		return contact;
	};
	public ContactRepository() {
	}
	public int saveMessage(Contact contact) {
		int res=0;
		String sql="insert into contact values(?,?,?,?,?)";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, contact.getName());
				ps.setString(2, contact.getEmail());
				ps.setString(3, contact.getCourse());
				ps.setString(4, contact.getMessage());
				ps.setString(5, "open");
			}
		});
	}
	public List<Contact> getAllContacts(){
		String sql="select * from contact where status='open'";
		return jdbcTemplate.query(sql, contactMapper);
		
	}
	public int changeStatus(String name) {
		String sql="update contact set status='disabled' where name=?";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, name);	
			}
		});
	}
	
}
