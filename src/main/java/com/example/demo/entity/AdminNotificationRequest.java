package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="admin_notifications")
public class AdminNotificationRequest {
	
	@Id
	private String uniqueId;
	
	private String userId;
	
	private String bookId;
	
	private Integer totalCopies;
	
	private Integer availableCopies;
	
	
	public String getUniqueId() {
		return uniqueId;
	}

	public 	void setUniqueId(String uniqueId) {
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

	public Integer getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Integer getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
	
	
}
