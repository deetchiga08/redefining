package com.briller.repository;

import com.briller.model.Gail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GailRepository extends JpaRepository<Gail,Long> {


    Gail findByPatientIdAndSurveyId(Long patientId,Long surveyId);
    Gail findByPatientId(Long patientId);
}
