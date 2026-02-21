package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_notifications")
public class UserNotificationRequest {
	
	@Id
	private String uniqueId;
	
	private String userId;
	
	private String bookId;
	
	private String bookName;
	
	private String bookAuthor;
	
	private String category;
	
	private String bookCopy;
	
	private String status;

	
	

	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(String bookCopy) {
		this.bookCopy = bookCopy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
