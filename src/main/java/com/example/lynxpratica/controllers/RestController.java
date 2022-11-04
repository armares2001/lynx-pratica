package com.example.lynxpratica.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lynxpratica.services.UserService;



@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path="/add") public String add(
			@RequestParam String name,
			@RequestParam String surname,
			@RequestParam String adress,
			@RequestParam String birthDate) {
		
		return userService.addUser(name,surname,adress,birthDate);
	}
	
	@PostMapping(path="/addBook")
	public String newBook(
			@RequestParam String title,
			@RequestParam String author,
			@RequestParam BigDecimal price,
			@RequestParam Integer stock,
			@RequestParam String category
			) {
		
		
		
		return "Libro inserito con successo!";
	}

}
