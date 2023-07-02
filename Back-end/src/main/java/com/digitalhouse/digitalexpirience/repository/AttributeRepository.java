package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.expirience.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM attributes WHERE id = :id AND deleted = 0 LIMIT 1")
    Optional<Attribute> findById(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM attributes WHERE names = :name AND deleted = 0 LIMIT 1")
    Optional<Attribute> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM attributes WHERE id = :id AND deleted = 0")
    Optional<Attribute> findByIds(@Param("id") Long id);
}
