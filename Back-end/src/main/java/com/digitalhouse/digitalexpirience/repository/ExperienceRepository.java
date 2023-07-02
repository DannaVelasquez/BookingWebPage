package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.expirience.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM experiences WHERE id = :id AND deleted = 0 LIMIT 1")
    Optional<Experience> findById(@Param("id") Long id);
    @Query(nativeQuery = true, value = "SELECT * FROM experiences WHERE titles = :title AND deleted = 0 LIMIT 1")
    Optional<Experience> findByTitle(@Param("title") String title);

    @Query(nativeQuery = true, value = "SELECT * FROM experiences WHERE deleted = 0")
    List<Experience> findRecommend();

    @Query(nativeQuery = true, value = "SELECT * FROM experiences WHERE category_id = :id AND deleted = 0")
    List<Experience> findAll(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM experiences e INNER JOIN places p ON e.place_id = p.id WHERE p.cities = :city AND e.deleted = 0")
    List<Experience> findAllForCity(@Param("city") String city);

    @Query(nativeQuery = true, value = "SELECT * FROM experiences WHERE category_id = :id AND deleted = 0")
    List<Experience> findAllForCategory(@Param("id") Long id);

}
