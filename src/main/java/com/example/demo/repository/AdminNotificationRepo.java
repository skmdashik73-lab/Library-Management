package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AdminNotificationRequest;

public interface AdminNotificationRepo extends JpaRepository<AdminNotificationRequest,String> {


	List<AdminNotificationRequest> findByStatus(String string);

	AdminNotificationRequest findByUniqueId(String uniqueid);

}
