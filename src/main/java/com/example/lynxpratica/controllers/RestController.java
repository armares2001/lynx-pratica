package com.example.lynxpratica.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.lynxpratica.entities.User;
import com.example.lynxpratica.repositories.UserRepository;



@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path="/add")
	@ResponseBody
	public String addUser(
			@RequestParam String name,
			@RequestParam String surname,
			@RequestParam String adress,
			@RequestParam String birthDate
			) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate date = LocalDate.parse(birthDate, formatter);
		java.util.Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		User n = new User();
		n.setName(name);
		n.setSurname(surname);
		n.setAdress(adress);
		n.setBirthDate(date1);
		userRepository.save(n);
		
		return "Utente salvato con successo!";
		
	}

}
