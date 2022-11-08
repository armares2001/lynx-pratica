package com.example.lynxpratica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lynxpratica.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	List<Book> findByTitle(String title);
	
}
