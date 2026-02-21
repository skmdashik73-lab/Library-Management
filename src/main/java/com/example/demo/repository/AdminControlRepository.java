package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserView;
import com.example.demo.entity.AllBooksData;

@Repository
public interface AdminControlRepository extends JpaRepository<AllBooksData,String>{

	AllBooksData findByBookId(String bookid);

	@Query("""
			SELECT a FROM AllBooksData a
			WHERE(a.bookName=:bookname)
			AND(a.bookAuthor=:bookauthor)
			AND(a.category=:bookcategory)
			""")
	AllBooksData findByBookNameAndBookAuthorAndCategory(
			@Param("bookname")String bookname, 
			@Param("bookauthor")String bookauthor, 
			@Param("bookcategory")String bookcategory);

	@Query("""
			SELECT a from AllBooksData a
			WHERE(:bookName IS NULL OR a.bookName=:bookName)
			AND(:bookAuthor IS NULL OR a.bookAuthor=:bookAuthor)
			AND(:category IS NULL OR a.category=:category)
			""")
	List<AllBooksData> filter(
			@Param("bookName")String bookName, 
			@Param("bookAuthor")String bookAuthor, 
			@Param("category")String category);

	Boolean deleteByBookId(String bookId);

	@Query("""
			SELECT a from AllBooksData a
			WHERE(:bookName IS NULL OR a.bookName=:bookName)
			AND(:bookAuthor IS NULL OR a.bookAuthor=:bookAuthor)
			AND(:category IS NULL OR a.category=:category)
			""")
	List<UserView> filterbydto(
			@Param("bookName")String bookName, 
			@Param("bookAuthor")String bookAuthor, 
			@Param("category")String category	
			);
	
	

	
}