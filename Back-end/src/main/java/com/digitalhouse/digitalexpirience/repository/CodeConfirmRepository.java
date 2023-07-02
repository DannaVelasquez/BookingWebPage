package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.CodeConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeConfirmRepository extends JpaRepository<CodeConfirm, Long> {

    Optional<CodeConfirm> findByCode(String code);


}
