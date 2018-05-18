package com.movingwalls.sample.sample1.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="user")
public class User {
	@Id
	private String id;
	
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate dateOfBirth;
}
