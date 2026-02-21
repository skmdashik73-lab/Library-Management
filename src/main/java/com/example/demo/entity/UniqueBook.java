package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class UniqueBook {
	@Id
	@Column(name="book_unique_id")
	private String bookUniqueId;
	
	private String status;
	@ManyToOne
	@JoinColumn(name="book_id")
	@JsonBackReference
	private AllBooksData uniqueBooksIds;
	
	
	public String getBookUniqueId() {
		return bookUniqueId;
	}
	public void setBookUniqueId(String bookUniqueId) {
		this.bookUniqueId = bookUniqueId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AllBooksData getUniqueBooksIds() {
		return uniqueBooksIds;
	}
	public void setUniqueBooksIds(AllBooksData uniqueBooksIds) {
		this.uniqueBooksIds = uniqueBooksIds;
	}
	
	
	
}
