package com.example.demo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class UserAccessIssuedBooks {
	
	@Id
	private String bookUniqueId;
	@Column(name="book_id")
	private String bookId;
	
	@Column(name="book_name")
	private String bookName;
	
	@Column(name="book_author")
	private String bookAuthor;
	
	private String category;
	
	@Column(name="issued_date")
	private LocalDate issuedDate;
	
	@Column(name="due_date")
	private LocalDate dueDate;
	
	private String fine;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonBackReference
	private UserData issuedBooksOfUser;

	
	

	public String getBookUniqueId() {
		return bookUniqueId;
	}

	public void setBookUniqueId(String bookUniqueId) {
		this.bookUniqueId = bookUniqueId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}

	

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
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

	public UserData getIssuedBooksOfUser() {
		return issuedBooksOfUser;
	}

	public void setIssuedBooksOfUser(UserData issuedBooksOfUser) {
		this.issuedBooksOfUser = issuedBooksOfUser;
	}
	
	
	
}
