package com.exercise.userservice.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByIdIn(List<Integer> ids, Pageable pageable);
	
}
