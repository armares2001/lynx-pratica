package com.example.lynxpratica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lynxpratica.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	

}
