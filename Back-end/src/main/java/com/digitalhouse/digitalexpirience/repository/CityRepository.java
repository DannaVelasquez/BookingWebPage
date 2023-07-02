package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.place.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM cities WHERE names = :name AND deleted = 0 LIMIT 1")
    Optional<City> findByName(@Param("name") String name);


    @Query(nativeQuery = true, value = "SELECT * FROM cities WHERE deleted = 0")
    List<City> findAll();

}
