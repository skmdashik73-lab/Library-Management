package com.example.demo.dto;


public class UserView {
	
	private String bookName;
	private String bookAuthor;
	private String category;
	private Integer availableCopies;
	
	public UserView(String bookName,String bookAuthor,String category,Integer availableCopies) {
		this.bookName=bookName;
		this.bookAuthor=bookAuthor;
		this.category=category;
		this.availableCopies=availableCopies;
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
	public Integer getAvailableCopies() {
		return availableCopies;
	}
	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}
	
	
	
}