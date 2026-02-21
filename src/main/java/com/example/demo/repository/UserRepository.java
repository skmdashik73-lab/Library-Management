package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserData;

public interface UserRepository extends JpaRepository<UserData,String> {

	UserData findByMail(String mail);

	UserData findByUserId(String userid);


}
