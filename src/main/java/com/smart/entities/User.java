package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@NotBlank(message = "User name can not be blank")
	@Size(min = 5, max = 15, message = "User name must be in between 5-15 characters ")
	private String userName;

	@Column(unique = true)
	@NotBlank(message = "Email can not be blank")
	private String userEmail;

	@NotBlank(message = "Age can not be blank")
	private String userAge;

	@NotBlank(message = "password can not be blank")
	private String userPassword;

	private String userRole;
	private boolean userEnabled;
	private String userImageUrl;

	@Column(length = 500)
	@NotBlank(message = "Introdcution is mandatory")
	private String userAbout;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Contact> contacts = new ArrayList<>();

	public User() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(boolean userEnabled) {
		this.userEnabled = userEnabled;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getUserAbout() {
		return userAbout;
	}

	public void setUserAbout(String userAbout) {
		this.userAbout = userAbout;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
