package com.example.lynxpratica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lynxpratica.entities.Category;
import com.example.lynxpratica.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public String addCategory(String category, Boolean isEnabled) {
		Category c = new Category();
		c.setIsEnabled(isEnabled);
		c.setName(category.toUpperCase());
		categoryRepository.save(c);
		if(isEnabled) {
			return "Categoria aggiunta con successo! UNDER 18 ANNI";
		}
		return "Categoria aggiunta con successo! OVER 18 ANNI";
	}

}
