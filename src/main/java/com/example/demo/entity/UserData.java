package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class UserData {
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Embedded
	private UserName userName;
	
	@Column(name="full_name")
	private String fullName;
	
	@Email
	private String mail;
	
	private String password;
	
	
	@Column(name="issued_books")
	private Integer issuedBooks;
	
	@OneToMany(mappedBy="issuedBooksOfUser",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	@JsonManagedReference
	List<UserAccessIssuedBooks> issued;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public UserName getUserName() {
		return userName;
	}

	public void setUserName(UserName userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setIssuedBooks(Integer issuedBooks) {
		this.issuedBooks = issuedBooks;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIssuedBooks() {
		return issuedBooks;
	}

	public void setIssuedBooks(int issuedBooks) {
		this.issuedBooks = issuedBooks;
	}

	public List<UserAccessIssuedBooks> getIssued() {
		return issued;
	}

	public void setIssued(List<UserAccessIssuedBooks> issued) {
		this.issued = issued;
	}
	
	
	
}
