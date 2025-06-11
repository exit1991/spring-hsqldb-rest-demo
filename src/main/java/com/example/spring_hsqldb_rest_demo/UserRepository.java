package com.example.spring_hsqldb_rest_demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    List<User> findAllByDeleteFlg(Boolean deleteFlg);
}
