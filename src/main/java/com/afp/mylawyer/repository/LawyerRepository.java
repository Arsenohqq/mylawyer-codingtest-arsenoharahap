package com.afp.mylawyer.repository;
import com.afp.mylawyer.domain.Lawyer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lawyer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

}
