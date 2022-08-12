package com.smart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="CONTACT")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	
	@NotBlank(message="Contact name can not be empty")
	private String contactName;
	
	@NotBlank(message = "Second name can not be blank")
	@Size(min=3,max = 6,message = "Second name must be in between 3-6 characters")
	private String contactSecondName;
	
	@NotBlank(message = "Contact E-mail can not be blank")
	private String contactEmail;
	
	private String contactOccupation;
	
	@NotNull(message = "Contact can not be blank")
	private Long contactPhone;
	
	@Column(length = 500)
	private String contactAbout;
	
	private String contactImageUrl;
	
    @ManyToOne()
    @JsonIgnore
	private User user;
	
	public Contact() {
		super();
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	

	public String getContactSecondName() {
		return contactSecondName;
	}

	public void setContactSecondName(String contactSecondName) {
		this.contactSecondName = contactSecondName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactOccupation() {
		return contactOccupation;
	}

	public void setContactOccupation(String contactOccupation) {
		this.contactOccupation = contactOccupation;
	}

	public Long getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(Long contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAbout() {
		return contactAbout;
	}

	public void setContactAbout(String contactAbout) {
		this.contactAbout = contactAbout;
	}

	public String getContactImageUrl() {
		return contactImageUrl;
	}

	public void setContactImageUrl(String contactImageUrl) {
		this.contactImageUrl = contactImageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", contactName=" + contactName + ", contactSecondName="
				+ contactSecondName + ", contactEmail=" + contactEmail + ", contactOccupation=" + contactOccupation
				+ ", contactPhone=" + contactPhone + ", contactAbout=" + contactAbout + ", contactImageUrl="
				+ contactImageUrl + ", user=" + user + "]";
	}

	
	
	
	
	
	
}
