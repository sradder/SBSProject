package com.onlinebanking.models;

// Generated Oct 19, 2014 4:55:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "pitchforkbank", uniqueConstraints = {
		@UniqueConstraint(columnNames = "phoneno"),
		@UniqueConstraint(columnNames = "ssn"),
		@UniqueConstraint(columnNames = "emailId") })
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6020795760959719504L;
	private String userId;
	private String emailId;
	private String password;
	private String fname;
	private String lname;
	private Date dob;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String ssn;
	private String phoneno;
	private String role;
	private int enabled;
	private String ques1;
	private String answer1;
	private String ques2;
	private String answer2;
	private String ques3;
	private String answer3;

	public User() {
		this.userId = UUID.randomUUID().toString();
		//TODO: Remove once registration form is complete.
		this.dob = new Date();
	}

	public User(String userId, String emailId, String password, String fname,
			String lname, Date dob, String address, String city, String state,
			String zipcode, String ssn, String phoneno, String role, int enabled,
			String ques1, String answer1, String ques2, String answer2,
			String ques3, String answer3) {
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.dob = dob;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.ssn = ssn;
		this.phoneno = phoneno;
		this.role = role;
		//TODO: Added for Admin approval purpose. 
		this.enabled = 1;
		this.ques1 = ques1;
		this.answer1 = answer1;
		this.ques2 = ques2;
		this.answer2 = answer2;
		this.ques3 = ques3;
		this.answer3 = answer3;
	}

	@Id
	@Column(name = "userId", unique = true, nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "emailId", unique = true, nullable = false, length = 45)
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "fname", nullable = false, length = 45)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "lname", nullable = false, length = 45)
	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dob", nullable = false, length = 0)
	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Column(name = "address", nullable = false, length = 45)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", nullable = false, length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", nullable = false, length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zipcode", nullable = false)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "ssn", unique = true, nullable = false, length = 45)
	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Column(name = "phoneno", unique = true, nullable = false)
	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "enabled", nullable = false)
	public int getEnabled() {
		return this.enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	@Column(name = "ques1", nullable = false, length = 100)
	public String getQues1() {
		return this.ques1;
	}

	public void setQues1(String ques1) {
		this.ques1 = ques1;
	}

	@Column(name = "answer1", nullable = false, length = 100)
	public String getAnswer1() {
		return this.answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	@Column(name = "ques2", nullable = false, length = 100)
	public String getQues2() {
		return this.ques2;
	}

	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}

	@Column(name = "answer2", nullable = false, length = 100)
	public String getAnswer2() {
		return this.answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	@Column(name = "ques3", nullable = false, length = 100)
	public String getQues3() {
		return this.ques3;
	}

	public void setQues3(String ques3) {
		this.ques3 = ques3;
	}

	@Column(name = "answer3", nullable = false, length = 100)
	public String getAnswer3() {
		return this.answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}


}
