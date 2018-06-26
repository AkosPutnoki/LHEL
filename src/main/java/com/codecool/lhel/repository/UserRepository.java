package com.codecool.lhel.repository;

import com.codecool.lhel.domain.userRelated.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User getUserByEmail(String email);

    public User getUserByName(String name);
    
}
