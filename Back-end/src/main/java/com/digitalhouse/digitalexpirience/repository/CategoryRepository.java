package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.expirience.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE id = :id AND deleted = 0 LIMIT 1")
    Optional<Category> findById(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE names = :name AND deleted = 0 LIMIT 1")
    Optional<Category> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE deleted = 0")
    List<Category> findAll();
}
