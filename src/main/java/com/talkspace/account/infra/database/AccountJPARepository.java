package com.talkspace.account.infra.database;

import com.talkspace.account.infra.database.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AccountJPARepository extends JpaRepository<AccountModel, String> {
    Optional<AccountModel> findByEmail(String email);
}
