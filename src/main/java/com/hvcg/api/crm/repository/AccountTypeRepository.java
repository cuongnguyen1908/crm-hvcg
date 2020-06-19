package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    Optional<AccountType> findAccountTypeByIdAndDeleteFlag(Long id, boolean status);
}