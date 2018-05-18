package com.movingwalls.sample.sample1.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.movingwalls.sample.sample1.domain.User;

public interface UserRepository extends MongoRepository<User, String>{
	Optional<User> findOneById(String id);
	Optional<User> findOneByName(String name);
	Optional<User> findOneByEmail(String email);
}
