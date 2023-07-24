package com.collegeproject.collegeproject.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.collegeproject.collegeproject.model.Assignment;
import com.collegeproject.collegeproject.model.AssignmentQuestions;
import com.collegeproject.collegeproject.model.AssignmentSubmission;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class AssignmentRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Assignment> mapper=(ResultSet rs,int count)->{
		Assignment assignment= new Assignment();
		assignment.setAssignmentNum(rs.getInt("assig_num"));
		assignment.setSubName(rs.getString("sub_Name"));
		assignment.setSubId(rs.getString("sub_id"));
		assignment.setDept(rs.getString("deptName"));
		assignment.setSection(rs.getString("section"));
		assignment.setSemNum(rs.getString("semNum"));
		return assignment;
	};
	private RowMapper<AssignmentQuestions> mapper1=(ResultSet rs,int count)->{
		AssignmentQuestions assignmentQuestions= new AssignmentQuestions();
		assignmentQuestions.setAssignNum(rs.getInt("id"));
		assignmentQuestions.setSubId(rs.getString("subId"));
		assignmentQuestions.setQuestion(rs.getString("qstn"));
		return assignmentQuestions;
	};
	private RowMapper<AssignmentSubmission> mapper2=(ResultSet rs,int count)->{
		List<String> answers= new ArrayList();
;		AssignmentSubmission assignmentSubmission= new AssignmentSubmission();
		assignmentSubmission.setAssignNum(rs.getInt("id"));
		assignmentSubmission.setSubId(rs.getString("subId"));
		assignmentSubmission.setRollNum(rs.getString("rollNum"));
		assignmentSubmission.setBatch(rs.getString("batch"));
		assignmentSubmission.setSection(rs.getString("section"));
		assignmentSubmission.setDept(rs.getString("dept"));
		String answer=rs.getString("ans");
		String[] arr=answer.split(" ");
		for(String s:arr)
			answers.add(s);
		assignmentSubmission.setAnswers(answers);
		return assignmentSubmission;
	};
	
	public int saveAssignment(Assignment assignment) {
		int result=0;
		String sql="insert into assignment values(?,?,?,?,?,?)";
		result=jdbcTemplate.update(sql, assignment.getAssignmentNum(),assignment.getSubName(),assignment.getSubId(),
				assignment.getDept(),assignment.getSection(),assignment.getSemNum());
		storeQuestions(assignment.getAssignmentNum(),assignment.getSubId(),assignment.getQuestions(),assignment.getSection());
		return result;
	}
	private void storeQuestions(int assignNum,String id,List<String> questions,String section) {
		 String sql="insert into questions values(?,?,?,?)";
		 questions.forEach(q->jdbcTemplate.update(sql,assignNum,q,id,section));
	}
	public List<Assignment> getAllAssignments(){
		String sql="select * from assignment";
		return jdbcTemplate.query(sql, mapper);
	}

	public List<AssignmentQuestions> getQuestions(int assignNum, String subjectId) {
		String sql = "select * from questions where id=? and subId=?";
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, assignNum);
				ps.setString(2, subjectId);
			}
		}, mapper1);
	}
	public boolean deleteAssignment(int assignNum,String subId,String section) {
		int assesResult=0;
		String sql1="delete from assignment where assig_num=? and sub_id=? and section=?";
		String sql2="delete from questions where id=? and subId=? and section=?";
		assesResult=jdbcTemplate.update(sql1, assignNum,subId,section);
		int qstnResult=jdbcTemplate.update(sql2, assignNum,subId,section);
		return assesResult>0 && qstnResult>0;
	}
	public boolean storeAssignment(AssignmentSubmission assignmentSubmission) {
		String sql="insert into submissions values(?,?,?,?,?,?)";
		String sql1="insert into answers values(?,?,?,?)";
		String submitAns="";
		boolean result=false;
		try {
			jdbcTemplate.update(sql, assignmentSubmission.getAssignNum(),assignmentSubmission.getSubId(),assignmentSubmission.getRollNum(),assignmentSubmission.getBatch(),assignmentSubmission.getSection(),assignmentSubmission.getDept());
		    //assignmentSubmission.getAnswers().forEach();
			List<String> answers=assignmentSubmission.getAnswers();
			log.info("Number of Answers submitted "+answers.size());
			for(String ans:answers) {
				submitAns+=ans+" ";
			}
			submitAns=submitAns.trim();
			jdbcTemplate.update(sql1,assignmentSubmission.getAssignNum(),assignmentSubmission.getSubId(),assignmentSubmission.getRollNum(),submitAns);
			return true;
		}catch(Exception ex) {
			return result;
		}
	}
	public List<AssignmentSubmission> getAllSubmissions(){
		String sql="select s.id,s.subId,s.rollNum,s.batch,s.section,s.dept,a.ans from submissions s, answers a where s.id=a.id and s.subId=a.subId and s.rollNum=a.rollNum";
		List<AssignmentSubmission> submissions=jdbcTemplate.query(sql, mapper2);
		return submissions;
	}

}
