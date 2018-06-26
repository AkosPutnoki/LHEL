package com.codecool.lhel.repository;

import com.codecool.lhel.domain.userRelated.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
