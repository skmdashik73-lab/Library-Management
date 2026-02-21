package com.example.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserView;
import com.example.demo.entity.AllBooksData;
import com.example.demo.entity.UniqueBook;
import com.example.demo.entity.UserAccessIssuedBooks;
import com.example.demo.entity.UserData;
import com.example.demo.entity.UserNotificationRequest;
import com.example.demo.repository.AdminControlRepository;
import com.example.demo.repository.AdminNotificationRepo;
import com.example.demo.repository.UserNotificationRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.AdminNotificationRequest;
import com.example.demo.entity.AdminUserAccessData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired 
	UserRepository userrepo;
	
	@Autowired
	AdminControlRepository booksrepo;
	
	@Autowired
	AdminNotificationRepo adminNtrepo;
	
	@Autowired
	UserNotificationRepo userNtrepo;

	public String userRegister(UserData userNew) {
		 if (userNew == null || userNew.getUserName() == null) {
	            return "Invalid User Data";
	        }
		String fName=userNew.getUserName().getFName();
		String LName=userNew.getUserName().getLName();
		String fullName=null;
		String password=userNew.getPassword();
		String mail=userNew.getMail();
		String userid=null;
		
		//Validation of inputs
		if(fName==null||fName.equals("")) {
			return "Give FirstName";
		}
		if(LName==null||LName.equals("")) {
			return "Give LastName";
		}
		if(mail==null||mail.equals("")) {
			return "Give MailId";
		}
		if(password==null||password.equals("")) {
			return "Password Required";
		}
		
		UserData existedUsermail=userrepo.findByMail(mail);
		if(existedUsermail!=null) {
			return "Mail already exited";
		}
		
		//Setting 
		fullName=fName+" "+LName;
		fullName=fullName.toUpperCase();
		userNew.setFullName(fullName);
		
		//generating numbers for userId
		Random rand = new Random();
		int num = rand.nextInt(90000) + 10000;
		fName=fName.toUpperCase();
		userid="#"+fName+num;
		UserData existedUserId=userrepo.findByUserId(userid);
		if(existedUserId!=null) {
			num = rand.nextInt(90000) + 10000;
			userid="#"+fName+num;
		}
		
		userNew.setUserId(userid);
		userNew.setIssuedBooks(0);
		
		userrepo.save(userNew);

		
		return "Registered Successfully";
	}

	public String userLogin(@Valid UserData userLogin) {
		String userid=userLogin.getUserId();
		String userMail=userLogin.getMail();
		String password=userLogin.getPassword();
		
		//validation
		if((userid==null||userid.equals(""))
				&&(userMail==null||userMail.equals(""))) {
			return "provide userid or Mail";
		}
		
		//checking in database
		if((userid==null||userid.equals(""))
				&&(userMail!=null)){
			if(password==null||password.equals("")) {
				return "provide password";
			}
			UserData existedemail=userrepo.findByMail(userMail);
			if(existedemail==null) {
				return "mail not exist";
			}
			if(existedemail!=null) {
				if(!(existedemail.getPassword().equals(password))) {
					return "Invalid Password";
				}else {
					return "Login succesfull";
				}
			}
			
		}
		
		if((userMail==null||userMail.equals(""))
				&&(userid!=null)){
			if(password==null||password.equals("")) {
				return "provide password";
			}
			UserData existeduserid=userrepo.findByUserId(userid);
			if(existeduserid==null) {
				return "UserId not exist";
			}
			if(existeduserid!=null) {
				if(!(existeduserid.getPassword().equals(password))) {
					return "Invalid Password";
				}else {
					return "Login succesfull";
				}
			}
			
		}
		return "Login succesfull";
	}

	public List<UserView> filterbooks(String bookName, String bookAuthor, String category) {
		return booksrepo.filterbydto(bookName,bookAuthor,category);
	}

	public String requestBook(AdminNotificationRequest request) {
		UserData existeduser=userrepo.findByUserId(request.getUserId());
		if(existeduser==null) {
			return "User not existed";
		}
		AllBooksData existedbook=booksrepo.findByBookId(request.getBookId());
		if(existedbook==null) {
			return "Book not existed";
		}
		request.setTotalCopies(existedbook.getTotalCopies());
		int available =existedbook.getAvailableCopies();
		if(available<1) {
			return "Copies not available";
		}
		request.setAvailableCopies(existedbook.getAvailableCopies());
		
		Random rand = new Random();
		int num = rand.nextInt(90) + 10;
		request.setUniqueId("#"+num);
		request.setStatus("PENDING");
		
		try {
			adminNtrepo.save(request);
		}
		catch(Exception e) {
			throw new RuntimeException("Problem when sending notification");
		}
		
		return "Request sent Successfully";
	}

	public AllBooksData findByNac(String bookname, String bookauthor, String bookcategory) {
		AllBooksData existedbook=booksrepo.findByBookNameAndBookAuthorAndCategory(bookname,bookauthor,bookcategory);
		return existedbook;
	}
	
	public List<UserNotificationRequest> getNotificationsForUser(String userId) {
        // This gets only the notifications for the person who is logged in
		System.out.println(userId);
        return userNtrepo.findByUserId(userId);
    }

	@Transactional
	@Modifying
	public String deleteNotification(String uniqueId) {
		userNtrepo.deleteByUniqueId(uniqueId);
		return "deleted successfully";
	}

	public List<UserAccessIssuedBooks> getBooks(String userId) {
		UserData existedUser=userrepo.findByUserId(userId);
		if(existedUser==null) {
			throw new RuntimeException("User not found with ID: " + userId);
		}
		
		List<UserAccessIssuedBooks> issuedBooks=existedUser.getIssued();
		
		return issuedBooks ;
	}

	@Transactional
	@Modifying
	public String returnBook(String userId, String bookUniqueId) {
		
		//validations
		UserData existedUser=userrepo.findByUserId(userId);
		if(existedUser==null) {
			return "User not found";
		}
		List<UserAccessIssuedBooks> userBooks=existedUser.getIssued();
		if(userBooks==null) {
			return "Books are not existed for "+userId;
		}
		
		
		//UserAccessIssuedBooks
		boolean uniqueStatus=false;
		String bookId=null;
		
		for(UserAccessIssuedBooks uniqueBook:userBooks) {
			if(uniqueBook.getBookUniqueId().equals(bookUniqueId)) {
				bookId=uniqueBook.getBookId();
				userBooks.remove(uniqueBook);
				uniqueStatus=true;
				break;
			}
		}
		if(!uniqueStatus) {
			return "Book is not existed this user";
		}

		//UserData
		existedUser.setIssuedBooks(existedUser.getIssuedBooks()-1);
		
		
		
		AllBooksData existedBook =booksrepo.findByBookId(bookId);
		if(existedBook==null) {
			return "BookId is not in AllBooksData";
		}
		
		
		//UniqueBook
		List<UniqueBook> uniquebooks=existedBook.getUniqueBookId();
		
		Boolean uniqeBookStatus=false;
		
		for(UniqueBook uniqueBook:uniquebooks) {
			if(uniqueBook.getBookUniqueId().equals(bookUniqueId)) {
				uniqeBookStatus=true;
				uniqueBook.setStatus("Available");
				break;
			}
		}
		if(!uniqeBookStatus) {
			return "UniqueBook not existed in  UniqueBook";
		}
		
		
		//AdminUserAccessData
		List<AdminUserAccessData> issuedTo=existedBook.getBooksIssuedTo();
		
		boolean bookIssuedStatus=false;
		
		for(AdminUserAccessData bookIssued:issuedTo) {
			if(bookIssued.getBookUniqueId().equals(bookUniqueId)) {
				bookIssuedStatus=true;
				issuedTo.remove(bookIssued);
				break;
			}
		}
		if(!bookIssuedStatus) {
			return "BookUniqueId is not available in IssuedBooksTo";
		}
		
		
		//AllBooksData
		existedBook.setAvailableCopies(existedBook.getAvailableCopies()+1);
		existedBook.setIssuedCopies(existedBook.getIssuedCopies()-1);
		
		
		booksrepo.save(existedBook);
		userrepo.save(existedUser);
	
		
		return "Returned Successfully";
	}

	
	
	
	

}
