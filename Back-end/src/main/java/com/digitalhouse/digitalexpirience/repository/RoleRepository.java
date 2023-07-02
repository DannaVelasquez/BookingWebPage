package com.digitalhouse.digitalexpirience.repository;


import com.digitalhouse.digitalexpirience.model.enums.RolEnum;
import com.digitalhouse.digitalexpirience.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RolEnum name);

}
