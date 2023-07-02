package com.digitalhouse.digitalexpirience.repository;


import com.digitalhouse.digitalexpirience.model.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM places WHERE names = :name AND deleted = 0 LIMIT 1")
    Optional<Place> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM places WHERE id = :id AND deleted = 0 LIMIT 1")
    Optional<Place> findById(@Param("id") Long id);


    @Query(nativeQuery = true, value = "SELECT * FROM places WHERE id = :id AND deleted = 0 LIMIT 1")
    List<Place> findAllById(@Param("id") List<Long> id);

}