package by.aston.bank.service;


import by.aston.bank.model.Account;
import by.aston.bank.repository.AccountRepository;

import by.aston.bank.repository.BankRepository;
import by.aston.bank.repository.UserRepository;
import by.aston.bank.service.dto.AccountReadDto;

import by.aston.bank.service.dto.AccountWriteDto;
import by.aston.bank.service.mapper.AccountMapper;
import by.aston.bank.service.mapper.AccountMapperImpl;
import by.aston.bank.utils.HibernateUtil;
import org.hibernate.Session;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {


    private final AccountRepository accountRepository = new AccountRepository();
    private final BankRepository bankRepository = new BankRepository();
    private final UserRepository userRepository = new UserRepository();
    private final AccountMapper accountMapper = new AccountMapperImpl();

    public Optional<AccountReadDto> findById(Long id) {
        try (var session = HibernateUtil.open()) {
            return Optional.ofNullable(
                    accountMapper.toReadDto(
                            accountRepository.findById(session, id).orElse(null)));
        }
    }

    public List<AccountReadDto> findAll() {
        try (var session = HibernateUtil.open()) {
            return accountRepository.findAll(session)
                    .stream()
                    .map(accountMapper::toReadDto)
                    .collect(Collectors.toList());
        }
    }

    public Optional<AccountReadDto> create(AccountWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var optAccount = createdUpdatedAccount(session, writeDto);
            var optAccountReadDto = optAccount.map(account -> accountMapper.toReadDto(
                    accountRepository.save(session, account)
                            .orElse(null)));
            session.getTransaction().commit();
            return optAccountReadDto;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public Optional<AccountReadDto> update(Long id, AccountWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var optAccount = createdUpdatedAccount(session, writeDto);
            var optAccountReadDto = optAccount.map(account -> accountMapper.toReadDto(
                    accountRepository.update(session, id, account)
                            .orElse(null)));
            session.getTransaction().commit();
            return optAccountReadDto;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public boolean delete(Long id) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var deleted =  accountRepository.delete(session, id);
            session.getTransaction().commit();
            return deleted;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    private Optional<Account> createdUpdatedAccount(Session session, AccountWriteDto writeDto) {
        var account = accountMapper.toEntity(writeDto);
        var optionalBank = bankRepository.findById(session, writeDto.bankId());
        var optionalUser = userRepository.findById(session, writeDto.userId());
        if (optionalBank.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }
        account.setBank(optionalBank.get());
        account.setUser(optionalUser.get());
        return Optional.of(account);
    }

}
