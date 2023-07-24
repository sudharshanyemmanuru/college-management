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

import com.collegeproject.collegeproject.model.Admin;

@Repository
public class AdminRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Admin> adminMapper = (ResultSet rs, int row) -> {
		Admin admin = new Admin();

		admin.setUserName(rs.getString("userName"));
		admin.setPassword(rs.getString("password"));

		return admin;
	};
	
	public Admin getAdmin(String userName,String password) {
		Admin admin = null;
		String sql="select * from admin where userName=? and password=?";
		List<Admin> admins=jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
				ps.setString(2, password);
				
			}
			
		},adminMapper);
		if(admins.size()>0)
			admin=admins.get(0);
		return admin;
	}
}
