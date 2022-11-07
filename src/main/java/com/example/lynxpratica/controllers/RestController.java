package com.example.lynxpratica.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lynxpratica.entities.Book;
import com.example.lynxpratica.entities.Category;
import com.example.lynxpratica.entities.User;
import com.example.lynxpratica.services.BookService;
import com.example.lynxpratica.services.CategoryService;
import com.example.lynxpratica.services.UserService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	Logger logger = LoggerFactory.getLogger(RestController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping(path="/addCategory")
	public String newCategory(
			@RequestParam Boolean isEnabled,
			@RequestParam String category
			) {
		logger.info("ADDED NEW CATEGORY WITH NAME: " + category );
		return categoryService.addCategory(category, isEnabled);
	}
	
	@PostMapping(path="/addUser")
	public String newUser(
			@RequestParam String name,
			@RequestParam String surname,
			@RequestParam String adress,
			@RequestParam String birthDate) throws ParseException {
		logger.info("ADDED NEW USER WITH NAME: " + name + ", SURNAME: " +surname+ ", ADRESS: "+adress+", BIRTHDAY: "+birthDate );
		return userService.addUser(name,surname,adress,birthDate);
	}
	
	@PostMapping(path="/addBook")
	public String newBook(
			@RequestParam String title,
			@RequestParam String author,
			@RequestParam BigDecimal price,
			@RequestParam Category category
			) {
		logger.info("ADDED NEW USER");
		return bookService.addBook(title, author, price, category);
	}
	
	@PutMapping(path="/addBalance/{id}")
	public String addBalance(
			@RequestParam BigDecimal balance,
			@PathVariable("id") Integer id
			) {
		logger.info("ADDED BALANCE AT USER " + userService.getUser(id).getName().toString());
		return userService.addBalance(balance, id);
	}

	@PutMapping("/wish/{id}/{idBook}")
	public String wishList(
			@PathVariable("id") Integer id,
			@PathVariable("idBook") Integer idBook
			) {
		logger.info("ADDED NEWBOOK IN WISHLIST OF " + userService.getUser(id).getName().toString() + ". BOOK ADDED: "  + bookService.getBook(idBook).getTitle().toString());
		return userService.wishList(id, idBook);
	}

	@PutMapping(path = "/buy/{id}/{idBook}")
	public String buyBook(
		@PathVariable("id") Integer id,
		@PathVariable("idBook") Integer idBook
	) {
		logger.info("THE USER WITH NAME: " + userService.getUser(id).getName().toString()+ " BOUGTH A BOOK WITH NAME: "+bookService.getBook(idBook).getTitle().toString());
		return userService.buyBook(id, idBook);
	}

	@DeleteMapping(path = "/delete/{idBook}")
	public String deleteBook(
			@PathVariable("idBook") Integer idBook
			) {
		logger.info("THE BOOK WITH NAME: " + bookService.getBook(idBook).getTitle().toString() + " HAS BEEN DELETED!" );
		return bookService.deleteBook(idBook);
	}

	@GetMapping(path = "/searchUser")
	public List<User> searchUser(
			@RequestParam String name,
			@RequestParam String surname,
			@RequestParam String birthDate
			) throws ParseException {
		return userService.searchUser(name, surname, birthDate);
	}

	@GetMapping(path = "/mywishlist/{id}")
	public List<Book> myWishList(
			@PathVariable("id") Integer id
			){
		logger.info("THE USER: " + userService.getUser(id).getName().toString() + " HAS SEARCH FOR HIS WISH LIST" );
		return userService.myWishList(id);
	}
	
	@GetMapping(path="/booksearch/{id}")
	public List<Book> searchBook(
			@PathVariable("id") Integer id
			){
		return  Optional.of(bookService.getBook(id)).stream().collect(Collectors.toList());
	}
	
	@PutMapping(path = "/refactorbook/{id}")
	public String refactBook(
			@PathVariable("id") Integer id,
			String newTitle,
			String newAuthor,
			@RequestParam BigDecimal newPrice,
			Integer newStock,
			@RequestParam Category newCategory
			) {
		logger.info("THE BOOK: " + bookService.getBook(id).getTitle().toString() + " HAS BEEN REFACTORED!" );
		return bookService.refactBook(id, newTitle, newAuthor, newPrice, newStock, newCategory);
	}
}
