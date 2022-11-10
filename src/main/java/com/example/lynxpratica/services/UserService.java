package com.example.lynxpratica.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.lynxpratica.entities.Book;
import com.example.lynxpratica.entities.User;
import com.example.lynxpratica.repositories.BookRepository;
import com.example.lynxpratica.repositories.UserRepository;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

	@Autowired
	private RestTemplate restTemplate;
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

	public String buyBook(Integer id, Integer idBook,Integer orderSize) {

		User n = this.getUser(id);
		Book b = this.getBook(idBook);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		restTemplate.exchange("http://localhost:8080/order/book/new/"+idBook+"/"+orderSize,
					HttpMethod.POST, entity, Boolean.class).getBody();

		List<Book> wishedList = n.getWishList();

		if (n.getBankAccount().compareTo(b.getPrice()) < 0) {
			return "Non hai un euro!";
		}
/*		if (b.getStock() <= 0) {
			return "Libro non disponibile";
		}*/

		
		n.setBankAccount(n.getBankAccount().subtract(b.getPrice()));
		for (int i = 0; i < wishedList.size(); i++) {
			if (wishedList.get(i).getId() == b.getId()) {
				wishedList.remove(i);
			}
		}
		n.setWishList(wishedList);
		b.setStock(b.getStock() - orderSize);
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
