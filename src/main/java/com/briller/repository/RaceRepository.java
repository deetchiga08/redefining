package com.briller.repository;

import com.briller.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface RaceRepository extends JpaRepository<Race,Long> {
}
