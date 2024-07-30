package by.aston.bank.repository;

import by.aston.bank.model.Bank;

public class BankRepository extends BaseRepository<Long, Bank> {


    public BankRepository() {
        super(Bank.class);
    }

}
