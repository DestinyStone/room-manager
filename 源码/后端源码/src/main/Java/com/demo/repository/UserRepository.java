package com.demo.repository;

import com.demo.entity.BusGroup;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
