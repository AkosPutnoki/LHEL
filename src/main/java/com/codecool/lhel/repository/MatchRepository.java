package com.codecool.lhel.repository;

import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {


    Match findFirstByUsersContainsOrderByIdDesc(UserEntity userEntity);
}
