package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class AllBooksData {
	
	@Id
	@Column(name="book_id" , nullable=false)
	private String bookId;
	
	@Column(name="book_name",nullable=false)
	private String bookName;
	
	@Column(name="book_author",nullable=false)
	private String bookAuthor;
	
	@Column(nullable=false)
	private String category;
	
	@Column(name="total_copies" , nullable=false)
	private Integer totalCopies;
	
	@Column(name="available_copies")
	private Integer availableCopies;
	
	
	@Column(name="issued_copies")
	private Integer issuedCopies;

	
	@OneToMany(mappedBy="issuedBooks", cascade=CascadeType.ALL,fetch=FetchType.LAZY , orphanRemoval=true )
	@JsonManagedReference
	List<AdminUserAccessData> booksIssuedTo;
	
	@OneToMany(mappedBy="uniqueBooksIds" ,cascade=CascadeType.ALL,fetch=FetchType.LAZY , orphanRemoval=true)
	@JsonManagedReference
	List<UniqueBook> uniqueBookId = new ArrayList<>();

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


	public Integer getIssuedCopies() {
		return issuedCopies;
	}


	public void setIssuedCopies(Integer issuedCopies) {
		this.issuedCopies = issuedCopies;
	}


	public List<UniqueBook> getUniqueBookId() {
		return uniqueBookId;
	}


	public void setUniqueBookId(List<UniqueBook> uniqueBookId) {
		this.uniqueBookId = uniqueBookId;
	}


	public List<AdminUserAccessData> getBooksIssuedTo() {
		return booksIssuedTo;
	}


	public void setBooksIssuedTo(List<AdminUserAccessData> booksIssuedTo) {
		this.booksIssuedTo = booksIssuedTo;
	}
	
	
	
}