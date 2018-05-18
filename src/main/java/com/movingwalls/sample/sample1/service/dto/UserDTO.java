package com.movingwalls.sample.sample1.service.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserDTO {
	private String id;
	
	private String name;
	private String firstName;
	private String lastName;

	private String email;
	private LocalDate dateOfBirth;
}
