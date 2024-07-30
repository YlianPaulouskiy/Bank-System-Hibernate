package by.aston.bank.repository;

import by.aston.bank.model.Account;

public class AccountRepository extends BaseRepository<Long, Account> {


    public AccountRepository() {
        super(Account.class);
    }

}
