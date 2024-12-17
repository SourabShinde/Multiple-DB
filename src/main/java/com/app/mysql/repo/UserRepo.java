package com.app.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.mysql.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
