package com.example.lynxpratica.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.lynxpratica.entities.Book;
import com.example.lynxpratica.entities.User;
import com.example.lynxpratica.repositories.BookRepository;
import com.example.lynxpratica.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;
	


	public User getUser(@PathVariable("id") Integer id) {
		Optional<User> n = userRepository.findById(id);
		return n.get();
	}

	public Book getBook(@PathVariable("id") Integer id) {
		Optional<Book> n = bookRepository.findById(id);
		return n.get();
	}

	public String addUser(String name, String surname, String adress, String birthDate) throws ParseException {

		java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
		BigDecimal indexAcc = BigDecimal.valueOf(0000.00);

		User n = new User();
		n.setName(name);
		n.setSurname(surname);
		n.setAdress(adress);
		n.setBirthDate(date1);
		n.setBankAccount(indexAcc);
		userRepository.save(n);

		return "Utente salvato con successo!";

	}

	public String addBalance(BigDecimal balance, Integer id) {
		User n = this.getUser(id);
		if (balance.compareTo(BigDecimal.ZERO) <= 0) {
			return "L'importo da caricare deve essere in positivo";
		}
		n.setBankAccount(balance.add(n.getBankAccount()));
		userRepository.save(n);
		return "Saldo aumentato con successo!";
	}

	public String wishList(Integer id, Integer idBook) {

		User n = this.getUser(id);
		Book b = this.getBook(idBook);

		List<Book> books = n.getWishList();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId() == b.getId()) {
				return "Libro gia presente in lista";
			}
		}

		books.add(b);
		n.setWishList(books);
		userRepository.save(n);
		return "Libro aggiunto alla tua lista!";
	}

	public String buyBook(Integer id, Integer idBook) {

		User n = this.getUser(id);
		Book b = this.getBook(idBook);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String nowDate = dateFormat.format(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(nowDate, formatter);
		
		LocalDate ageUserDate = n.getBirthDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		Integer period = Period.between(ageUserDate, localDate).getYears();
		
		List<Book> wishedList = n.getWishList();

		if (n.getBankAccount().compareTo(b.getPrice()) < 0) {
			return "Non hai un euro!";
		}
		if (b.getStock() <= 0) {
			return "Libro non disponibile";
		}
		
		if(period < 18 && !b.getCategory().getIsEnabled()) {
			return "Il libro è adatto a lettori di età adulta!";
		}
		
		n.setBankAccount(n.getBankAccount().subtract(b.getPrice()));
		for (int i = 0; i < wishedList.size(); i++) {
			if (wishedList.get(i).getId() == b.getId()) {
				wishedList.remove(i);
			}
		}
		n.setWishList(wishedList);
		b.setStock(b.getStock() - 1);
		bookRepository.save(b);
		userRepository.save(n);

		return "Libro comprato con successo";
	}

	public List<User> searchUser(String name, String surname, String birthDate) throws ParseException {

		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
		return userRepository.findByNameAndSurnameAndBirthDate(name, surname, date1);

	}

	public List<Book> myWishList(Integer id) {
		User n = this.getUser(id);
		return n.getWishList();
	}
}
