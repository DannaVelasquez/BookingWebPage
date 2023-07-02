package com.digitalhouse.digitalexpirience.repository;


import com.digitalhouse.digitalexpirience.model.place.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM countries WHERE names = :name AND deleted = 0 LIMIT 1")
    Optional<Country> findByName(@Param("name") String name);
}
