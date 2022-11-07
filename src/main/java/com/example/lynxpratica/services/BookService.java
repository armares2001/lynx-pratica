package com.example.lynxpratica.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.lynxpratica.entities.Book;
import com.example.lynxpratica.entities.Category;
import com.example.lynxpratica.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book getBook(@PathVariable("id") Integer id) {
		Optional<Book> n = bookRepository.findById(id);
		return n.get();
	}
	
	public String addBook(String title,String author,BigDecimal price, Category category) {
		
		Book b = new Book();
		b.setTitle(title);
		b.setAuthor(author);
		b.setPrice(price);
		b.setStock(1);
		b.setCategory(category);
		bookRepository.save(b);
		
		
		return "Libro inserito con successo!";
		
	}

	public String deleteBook(Integer id) {
		Book b = this.getBook(id);
		if(!b.getList().isEmpty()) {
			return "Il libro è presente in una wish list e non può essere eliminato!";
		}
		bookRepository.delete(b);
		return "Libro cestinato!";
	}

	public String refactBook(
			Integer id,
			String newTitle,
			String newAuthor,
			BigDecimal newPrice,
			Integer newStock,
			Category newCategory) {
		
		Book b = this.getBook(id);
		
		if(newTitle == null || newTitle.isEmpty()) {
			b.setTitle(b.getTitle());
		}else {
			b.setTitle(newTitle);
		}
		
		if(newAuthor == null || newAuthor.isEmpty()) {
			b.setAuthor(b.getAuthor());
		}else {
			b.setAuthor(newAuthor);
		}

		if(newStock == null || newStock==0) {
			b.setStock(b.getStock());
		}else {
			b.setStock(newStock);
		}
		
		b.setPrice(newPrice);
		b.setCategory(newCategory);
		bookRepository.save(b);
		
		return "Libro modificato con successo";
	}
	
}
