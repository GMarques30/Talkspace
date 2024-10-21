package com.talkspace.account.infra.repository;

import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;
import com.talkspace.account.infra.database.AccountJPARepository;
import com.talkspace.account.infra.database.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryDatabase implements AccountRepository {
    @Autowired
    private AccountJPARepository db;


    @Override
    public void save(Account account) {
        AccountModel model = new AccountModel(account.accountId, account.getFirstName(), account.getLastName(), account.getEmail(), account.getPassword(), account.createdAt);
        this.db.save(model);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        Optional<AccountModel> model = this.db.findByEmail(email);
        return model.map(mdl -> new Account(
                mdl.getAccountId(),
                mdl.getFirstName(),
                mdl.getLastName(),
                mdl.getEmail(),
                mdl.getPassword(),
                mdl.getCreatedAt()
        ));
    }

    @Override
    public Optional<Account> findByAccountId(String accountId) {
        Optional<AccountModel> model = this.db.findById(accountId);
        return model.map(mdl -> new Account(
                mdl.getAccountId(),
                mdl.getFirstName(),
                mdl.getLastName(),
                mdl.getEmail(),
                mdl.getPassword(),
                mdl.getCreatedAt()
        ));
    }
}
