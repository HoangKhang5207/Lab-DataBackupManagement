package com.khang.databackup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khang.databackup.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
