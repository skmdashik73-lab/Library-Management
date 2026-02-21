package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminNotificationRequest;
import com.example.demo.entity.AdminUserAccessData;
import com.example.demo.entity.AllBooksData;
import com.example.demo.entity.UniqueBook;
import com.example.demo.entity.UserAccessIssuedBooks;
import com.example.demo.entity.UserData;
import com.example.demo.entity.UserNotificationRequest;
import com.example.demo.repository.AdminControlRepository;
import com.example.demo.repository.AdminNotificationRepo;
import com.example.demo.repository.UserNotificationRepo;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService{
	
	@Autowired 
	UserRepository userrepo;
	@Autowired
	AdminControlRepository booksrepo;
	
	@Autowired
	AdminNotificationRepo adminNtrepo;
	
	@Autowired
	UserNotificationRepo userNtrepo;

	public List<AllBooksData> filterbooks(String bookName, String bookAuthor, String category) {
		
		return booksrepo.filter(bookName,bookAuthor,category);
	}
	
	
	public String addBooks(AllBooksData books) {
		
		//User input variable assigning
		
		String bookid = books.getBookId();
		String bookname=books.getBookName();
		String bookauthor=books.getBookAuthor();
		String bookcategory=books.getCategory();
		Integer totalcopies=books.getTotalCopies();
			
		//user input validation
		
		if(bookid==null||bookid.isBlank()) {
			return "Provide BookId";
		}
		if(bookname==null||bookname.isBlank()) {
			return "Provide BookName";
		}
		if(bookauthor==null||bookauthor.isBlank()) {
			return "Provide BookAuthor";
		}
		if(bookcategory==null||bookcategory.isBlank()) {
			return "Provide BookCategory";
		}
		if(totalcopies==null||totalcopies<1) {
			return "Total copies must be provided and copies>0";
		}
		//checking if have differnet bookid but same book details
		AllBooksData existedBookOrNot=booksrepo.findByBookNameAndBookAuthorAndCategory(bookname,bookauthor,bookcategory);
		if(existedBookOrNot!=null && !bookid.equals(existedBookOrNot.getBookId())) {
			return "there is already a book with same name , author, category";
		}
		
		//saving in database
		
		//checking in database if book already existed or not 
		
		AllBooksData existedBook=booksrepo.findByBookId(bookid);
		if(existedBook!=null) {
			String existedbookname=existedBook.getBookName();
			String existedbookauthor=existedBook.getBookAuthor();
			String existedbookcategory=existedBook.getCategory();
			Integer existedtotalcopies=existedBook.getTotalCopies();
			Integer existedavailablecopies=existedBook.getAvailableCopies();
			List <UniqueBook> existeduniquebooks=existedBook.getUniqueBookId();
			
			//checking if given bookid has same details in database  
			if(bookid.equals(existedBook.getBookId())
					&& (!bookname.equals(existedbookname)
					|| !bookauthor.equals(existedbookauthor)
					|| !bookcategory.equals(existedbookcategory))) {
				return "bookid already existed";
			}
			try {
				existedBook.setTotalCopies(existedtotalcopies+totalcopies);
				existedBook.setAvailableCopies(existedavailablecopies+totalcopies);
				int uniqueBookNumber = 0;

				for (UniqueBook uniquebook : existeduniquebooks) {
				    String uniquebookid = uniquebook.getBookUniqueId();
				    String numberPart = uniquebookid.replace(bookid + "U", "");
				    int currentNumber = Integer.parseInt(numberPart);
				    if (currentNumber > uniqueBookNumber) {
				        uniqueBookNumber = currentNumber;
				    }
				}
				uniqueBookNumber = uniqueBookNumber + 1;

				for(int i=1 ; i<=totalcopies; i++) {
					String uniqueBookId=bookid+"U"+uniqueBookNumber;
					UniqueBook uniquebook = new UniqueBook();
					uniquebook.setBookUniqueId(uniqueBookId);
					uniquebook.setStatus("Available");
					uniquebook.setUniqueBooksIds(existedBook);
					existedBook.getUniqueBookId().add(uniquebook);
					uniqueBookNumber++;
				}
				booksrepo.save(existedBook);
			}
			catch(Exception e) {
				throw e;
			}
			return "Added copies successfully";
		}
		
		//if the book is not in data base
		if(existedBook==null) {
			try {
				books.setAvailableCopies(totalcopies);
				books.setIssuedCopies(0);
				for(int i=1 ; i<=totalcopies; i++) {
					String uniqueBookPrefix=bookid+"U";
					UniqueBook uniquebook = new UniqueBook();
					uniquebook.setBookUniqueId(uniqueBookPrefix+i);
					uniquebook.setStatus("Available");
					uniquebook.setUniqueBooksIds(books);
					books.getUniqueBookId().add(uniquebook);
				}
				booksrepo.save(books);
			}
			catch(Exception e) {
				throw e;
			}	
		}
		
		return "Added Successfully";
	}

	public String deleteById(String bookId) {
		if(bookId==null||bookId.isBlank()) {
			return "Book not found";
		}
		AllBooksData result=booksrepo.findByBookId(bookId);
		if(result !=null){
			booksrepo.deleteById(bookId);
			return "Book deleted Successfully";
		}
		else {
			return "book not found";
		}
	}

	
	public String deleteuniquebook(String bookId, String uniqueid) {
		
		AllBooksData existedbook=booksrepo.findByBookId(bookId);
		if(existedbook==null) {
			return "bookid is not existed";
		}
		List<UniqueBook> uniquebooks=existedbook.getUniqueBookId();
		Boolean result=false;
		try {
			boolean status=false;
			for(UniqueBook uniquebook:uniquebooks) {
				if(uniquebook.getBookUniqueId().equals(uniqueid)) {
					status=true;
					String bookStatus=uniquebook.getStatus();
					uniquebooks.remove(uniquebook);
					existedbook.setTotalCopies(existedbook.getTotalCopies()-1);
					if(bookStatus.equals("Available")) {
						existedbook.setAvailableCopies(existedbook.getAvailableCopies()-1);
						booksrepo.save(existedbook);
						result=true;
					}else {
						existedbook.setIssuedCopies(existedbook.getIssuedCopies());
						booksrepo.save(existedbook);
						result=true;
					}
					break;
				}
			}
			if(!status) {
				return "Book copy does not exist";
			}
			
		}catch(Exception e) {
			throw e;
		}
		if(result) {
			return "Copy deleted successfully";
		}else {
			return "Copy is not deleted";
		}
	}


	public List<UniqueBook> getuniquebook(String bookId) {
		// TODO Auto-generated method stub
		AllBooksData existedBook= booksrepo.findByBookId(bookId);
		List<UniqueBook> uniquebooks = existedBook.getUniqueBookId();
		return uniquebooks;
		
	}


	public List<AdminNotificationRequest> seeNotification() {
		List<AdminNotificationRequest> notifications=adminNtrepo.findByStatus("PENDING");
		return notifications;
	}


	@Transactional
	public String responseToUser(AdminNotificationRequest adminnotification) {
		String bookId=adminnotification.getBookId();
		String userId=adminnotification.getUserId();
		String status=adminnotification.getStatus().toUpperCase();
		String uniqueid=adminnotification.getUniqueId();
		
		if(bookId==null||bookId.isBlank()) {
			return "Provide BookId";
		}
		if(userId==null||userId.equals("")) {
			return "provide userid";
		}
		if(status==null||status.equals("")) {
			return "provide userid";
		}
		if(uniqueid==null||uniqueid.equals("")) {
			return "provide uniquenoyification id";
		}
		AllBooksData existedbook=booksrepo.findByBookId(bookId);
		if(existedbook==null) {
			return "bookid is not existed";
		}
		
		UserData existeduserid=userrepo.findByUserId(userId);
		if(existeduserid==null) {
			return "UserId not exist";
		}
		AdminNotificationRequest notificationid=adminNtrepo.findByUniqueId(uniqueid);
		if(notificationid==null) {
			return "notification id not existed";
		}
		
		if(status.equals("REJECTED")) {
			//setting columns in adminnotification
			notificationid.setStatus(status);
			
			adminNtrepo.save(notificationid);
			
			
			UserNotificationRequest userNotification=new UserNotificationRequest();
			
			try {
				//setting columns in usernotification
				userNotification.setUniqueId(uniqueid);
				userNotification.setUserId(userId);
				userNotification.setBookId(bookId);
				userNotification.setBookName(existedbook.getBookName());
				userNotification.setBookAuthor(existedbook.getBookAuthor());
				userNotification.setCategory(existedbook.getCategory());
				userNotification.setBookCopy(status);
				userNotification.setStatus(status);
				userNtrepo.save(userNotification);
			}catch(Exception e) {
				throw e;
			}
		}
		else if (status.equals("ACCEPTED")) {

		    if (existedbook.getAvailableCopies() <= 0) {
		        return "No books available";
		    }

		    notificationid.setStatus(status);
		    notificationid.setAvailableCopies(notificationid.getAvailableCopies() - 1);
		    adminNtrepo.save(notificationid);

		    existedbook.setAvailableCopies(existedbook.getAvailableCopies() - 1);
		    existedbook.setIssuedCopies(existedbook.getIssuedCopies() + 1);

		    List<UniqueBook> uniquebooks = existedbook.getUniqueBookId();
		    UniqueBook result = null;

		    for (UniqueBook uniquebook : uniquebooks) {
		        if ("Available".equals(uniquebook.getStatus())) {
		            result = uniquebook;
		            uniquebook.setStatus("Not Available");
		            break;
		        }
		    }

		    if (result == null) {
		        return "No books available";
		    }

		    AdminUserAccessData bookIssuedTo = new AdminUserAccessData();
		    bookIssuedTo.setBookUniqueId(result.getBookUniqueId());
		    bookIssuedTo.setUserId(userId);
		    bookIssuedTo.setUserName(existeduserid.getFullName());
		    bookIssuedTo.setIssuedBooks(existedbook);

		    if (existedbook.getBooksIssuedTo() == null) {
		        existedbook.setBooksIssuedTo(new ArrayList<>());
		    }

		    existedbook.getBooksIssuedTo().add(bookIssuedTo);
		    booksrepo.save(existedbook);

		    UserNotificationRequest userNotification = new UserNotificationRequest();
		    userNotification.setUniqueId(uniqueid);
		    userNotification.setUserId(userId);
		    userNotification.setBookId(bookId);
		    userNotification.setBookName(existedbook.getBookName());
		    userNotification.setBookAuthor(existedbook.getBookAuthor());
		    userNotification.setCategory(existedbook.getCategory());
		    userNotification.setBookCopy(result.getBookUniqueId());
		    userNotification.setStatus(status);

		    userNtrepo.save(userNotification);

		    existeduserid.setIssuedBooks(existeduserid.getIssuedBooks() + 1);

		    List<UserAccessIssuedBooks> booksIssued = existeduserid.getIssued();
		    if (booksIssued == null) {
		        booksIssued = new ArrayList<>();
		        existeduserid.setIssued(booksIssued);
		    }

		    UserAccessIssuedBooks userBook = new UserAccessIssuedBooks();
		    userBook.setBookAuthor(existedbook.getBookAuthor());
		    userBook.setBookId(bookId);
		    userBook.setBookName(existedbook.getBookName());
		    userBook.setBookUniqueId(result.getBookUniqueId());
		    userBook.setCategory(existedbook.getCategory());
		    userBook.setIssuedBooksOfUser(existeduserid);

		    booksIssued.add(userBook);

		    userrepo.save(existeduserid);
		}
		return "Response sent successfully";
	}
	
	

}