package com.example.demo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class AdminUserAccessData {
	
	@Id
	private String bookUniqueId;
	
	
	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="issued_date")
	private LocalDate issuedDate;
	
	@Column(name="due_date")
	private LocalDate dueDate;
	
	private String fine;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	@JsonBackReference
	private AllBooksData issuedBooks;

	
	
	public String getBookUniqueId() {
		return bookUniqueId;
	}

	public void setBookUniqueId(String bookUniqueId) {
		this.bookUniqueId = bookUniqueId;
	}
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public AllBooksData getIssuedBooks() {
		return issuedBooks;
	}

	public void setIssuedBooks(AllBooksData issuedBooks) {
		this.issuedBooks = issuedBooks;
	}
	
	
}
