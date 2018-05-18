package com.movingwalls.sample.sample1.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movingwalls.sample.sample1.domain.User;
import com.movingwalls.sample.sample1.repository.UserRepository;
import com.movingwalls.sample.sample1.service.dto.UserDTO;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> fetchById(String id) {
		return userRepository.findOneById(id);
	}
	
	public Optional<User> fetchByName(String name) {
		return userRepository.findOneByName(name);
	}
	
	public Optional<User> fetchByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
	public List<User> fetchAll() {
		return userRepository.findAll();
	}
	
	public String save(UserDTO userDTO) {
		// validation is required
		if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
			return "user name is required";
		}
		
		if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
			return "user email is required";
		}
		
		// duplication
		// name already exists
		Optional<User> existingUser = userRepository.findOneByName(userDTO.getName());
		
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			return "user name already exists";
		}
		
		existingUser = userRepository.findOneByEmail(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			return "user email already exists";
		}
		
		// create or update
		Optional<User> userOpt = userRepository.findOneById(userDTO.getId());
		User newUser;
		
		if (userOpt.isPresent()) {
			newUser = userOpt.get();
		} else {
			newUser = new User();
		}
		
		newUser.setName(userDTO.getName());
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail()); 
		newUser.setDateOfBirth(userDTO.getDateOfBirth());
		
		// save
		
		userRepository.save(newUser);
		
		
		
		return "success";
	}
	
	public void delete(String id) {
		Optional<User> user = userRepository.findOneById(id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
	}
}
