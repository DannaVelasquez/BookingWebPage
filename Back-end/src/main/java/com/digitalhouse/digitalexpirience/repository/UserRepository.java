package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE user_names = :user_name AND deleted = 0 LIMIT 1")
    Optional<User> findByUserName(@Param("user_name") String username);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE user_names = :user_name AND passwords = :password AND deleted = 0 LIMIT 1")
    Optional<User> findByUserName(@Param("user_name") String username, @Param("password") String password);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE id = :id AND deleted = false LIMIT 1")
    Optional<User> findById(@Param("id") Long id);

    Optional<User> findByUsername(String username);


}
