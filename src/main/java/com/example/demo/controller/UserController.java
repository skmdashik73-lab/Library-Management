package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UserView;
import com.example.demo.entity.AdminNotificationRequest;
import com.example.demo.entity.AllBooksData;
import com.example.demo.entity.UserAccessIssuedBooks;
import com.example.demo.entity.UserData;
import com.example.demo.entity.UserNotificationRequest;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@PostMapping("/register")
	public String userRegistration(@Valid @RequestBody UserData userNew) {
		return userservice.userRegister(userNew);
	}
	@PostMapping("/login")
	public String userLogin(@Valid @RequestBody UserData userLogin) {
		return userservice.userLogin(userLogin);
	}
	@GetMapping("/filterbooks")
	public List<UserView> filterBooks(
			@RequestParam(required=false) String bookName,
			@RequestParam(required=false) String bookAuthor,
			@RequestParam(required=false) String category
			){
		return userservice.filterbooks(bookName,bookAuthor,category);
	}
	@PostMapping("/requestbook")
	public String requestBook(@RequestBody AdminNotificationRequest request) {
		
		return userservice.requestBook(request);
	}
	@GetMapping("/mynotifications/{userId}")
    public List<UserNotificationRequest> seeMyNotifications(@PathVariable String userId) {
        return userservice.getNotificationsForUser(userId);
    }
	@GetMapping("/findbynac/{bookname}/{bookauthor}/{bookcategory}")
	public AllBooksData findByNac(@PathVariable String bookname,@PathVariable String bookauthor,@PathVariable String bookcategory) {
		return userservice.findByNac(bookname,bookauthor,bookcategory);
	}
	@DeleteMapping("/deletenotification/{uniqueId}")
	public String deleteNotification(@PathVariable String uniqueId) {
		return userservice.deleteNotification(uniqueId);
	}
	@GetMapping("/getBooks/{userId}")
	public List<UserAccessIssuedBooks> getBooks(@PathVariable String userId){
		return userservice.getBooks(userId);
	}
	@GetMapping("/returnbook/{userId}/{bookUniqueId}")
	public String returnBook(@PathVariable String userId,
			@PathVariable String bookUniqueId) {
		return userservice.returnBook(userId,bookUniqueId);
	}
}
