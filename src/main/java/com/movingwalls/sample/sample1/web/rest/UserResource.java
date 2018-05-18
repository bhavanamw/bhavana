package com.movingwalls.sample.sample1.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movingwalls.sample.sample1.domain.User;
import com.movingwalls.sample.sample1.service.UserService;
import com.movingwalls.sample.sample1.service.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class UserResource {
	private static final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<?> getAllUser(@RequestParam(required=false) String name, @RequestParam(required=false) String email) {
		if (name != null) {
			Optional<User> user = userService.fetchByName(name);
			if (user.isPresent()) {
				return ResponseEntity.ok(user.get());
			}

			return ResponseEntity.badRequest().body("user name not found");
		} else if (email != null) {
			Optional<User> user = userService.fetchByEmail(email);
			if (user.isPresent()) {
				return ResponseEntity.ok(user.get());
			}

			return ResponseEntity.badRequest().body("user email not found");
		}

		List<User> users = userService.fetchAll();
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable String id) {
		Optional<User> user = userService.fetchById(id);

		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}

		return ResponseEntity.badRequest().body("invalid user id");
	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		if (userDTO.getId() != null) {
			return ResponseEntity.badRequest().body("new user should not have user id");
		}

		String status = userService.save(userDTO);

		if ("success".equals(status)) {
			return ResponseEntity.ok(status);
		}

		return ResponseEntity.badRequest().body(status);
	}

	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
		if (userDTO.getId() == null || userDTO.getId().isEmpty()) {
			return createUser(userDTO);
		}

		String status = userService.save(userDTO);

		if ("success".equals(status)) {
			return ResponseEntity.ok(status);
		}

		return ResponseEntity.badRequest().body(status);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		userService.delete(id);
		return null;
	}
}

