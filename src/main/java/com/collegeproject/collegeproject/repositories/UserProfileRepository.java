package com.collegeproject.collegeproject.repositories;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.collegeproject.collegeproject.model.UserProfile;

@Repository
public class UserProfileRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<UserProfile> mapper=(ResultSet rs,int count)->{
		UserProfile profile = new UserProfile();
		profile.setId(rs.getInt("id"));
		profile.setName(rs.getString("name"));
		profile .setBio(rs.getString("bio"));
		return profile;
	};
	public int updateProfile(String bio, int id) {
		String sql="update user_profile set bio=? where id=?";
		return jdbcTemplate.update(sql,bio,id);
	}
	public UserProfile getProfileById(int id) {
		String sql="select * from user_profile where id=?";
		return (UserProfile) jdbcTemplate.queryForList(sql, id,mapper ).get(0);
	}
}
