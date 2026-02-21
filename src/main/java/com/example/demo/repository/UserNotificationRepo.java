package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserNotificationRequest;

public interface UserNotificationRepo extends JpaRepository<UserNotificationRequest,String> {

	UserNotificationRequest findByUniqueId(String uniqueId);


	void deleteByUniqueId(String uniqueId);

	List<UserNotificationRequest> findByUserId(String userId);

}
