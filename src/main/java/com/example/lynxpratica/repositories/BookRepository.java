package com.example.lynxpratica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lynxpratica.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	
	
}
