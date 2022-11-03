package com.example.lynxpratica.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lynxpratica.entities.User;
import com.example.lynxpratica.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public String addUser(
			String name,
			String surname,
			String adress,
			String birthDate
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
