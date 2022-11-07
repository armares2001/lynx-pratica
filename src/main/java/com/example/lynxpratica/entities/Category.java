package com.example.lynxpratica.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="category")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="category", length = 10)
	private String name;
	
	@Column(name= "is_enabled")
	private Boolean isEnabled;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Book> book;

}
