package com.codecool.lhel.repository;

import com.codecool.lhel.domain.userRelated.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity getUserByName(String name);
    
}
