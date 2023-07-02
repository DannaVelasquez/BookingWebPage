package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.expirience.Politic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticRepository extends JpaRepository<Politic, Long> {

}
