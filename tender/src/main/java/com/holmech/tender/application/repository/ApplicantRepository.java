package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Applicant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicantRepository extends CrudRepository<Applicant, Long> {

    Applicant findByNameA(String nameA);
}
