package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.AdminNotificationRequest;
import com.example.demo.entity.AllBooksData;
import com.example.demo.entity.UniqueBook;
import com.example.demo.service.AdminService;


@RestController

@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController{
	
	@Autowired
	AdminService adminservice;
	
	@GetMapping("/filterbooks")
	public List<AllBooksData> filterBooks(
			@RequestParam(required=false) String bookName,
			@RequestParam(required=false) String bookAuthor,
			@RequestParam(required=false) String category
			){
		return adminservice.filterbooks(bookName,bookAuthor,category);
	}
	
	@PostMapping("/addbooks")
	public String addingBooks(@RequestBody AllBooksData Books) {
		return adminservice.addBooks(Books);
	}
	@DeleteMapping("/deletebookbyId/{bookId}")
	public String deleteById(@PathVariable String bookId) {
		return adminservice.deleteById(bookId);
	}
	@GetMapping("/getuniquebooks/{bookId}")
	public List<UniqueBook> getuniqueBook(@PathVariable String bookId){
		return adminservice.getuniquebook(bookId);
	}
	@DeleteMapping("/deleteuniquebook/{bookId}/{uniqueid}")
	public String deleteByUniqueId(@PathVariable String bookId,
			@PathVariable String uniqueid) {
		return adminservice.deleteuniquebook(bookId,uniqueid);
	}
	@GetMapping("/seenotifications")
	public List<AdminNotificationRequest> seeNotifications(){
		return adminservice.seeNotification();
	}
	@PostMapping("/response")
	public String responseToUser(@RequestBody AdminNotificationRequest adminnotification) {
		return adminservice.responseToUser(adminnotification);
	}
	
	
}