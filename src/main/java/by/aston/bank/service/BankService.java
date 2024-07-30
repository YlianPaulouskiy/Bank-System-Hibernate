package by.aston.bank.service;

import by.aston.bank.repository.BankRepository;
import by.aston.bank.service.dto.BankReadDto;
import by.aston.bank.service.dto.BankUserDto;
import by.aston.bank.service.dto.BankWriteDto;
import by.aston.bank.service.mapper.BankMapper;
import by.aston.bank.service.mapper.BankMapperImpl;
import by.aston.bank.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankService {

    private final BankRepository bankRepository = new BankRepository();
    private final BankMapper bankMapper = new BankMapperImpl();

    public Optional<BankReadDto> findById(Long id) {
        try (var session = HibernateUtil.open()) {
            return Optional.ofNullable(
                    bankMapper.toReadDto(
                            bankRepository.findById(session, id).orElse(null)));
        }
    }

    public List<BankReadDto> findAll() {
        try (var session = HibernateUtil.open()) {
            return bankRepository.findAll(session)
                    .stream()
                    .map(bankMapper::toReadDto)
                    .collect(Collectors.toList());
        }
    }

    public Optional<BankUserDto> create(BankWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var optBankReadDto = Optional.ofNullable(
                    bankMapper.toUserDto(
                            bankRepository.save(session, bankMapper.toEntity(writeDto))
                                    .orElse(null)));
            session.getTransaction().commit();
            return optBankReadDto;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public Optional<BankReadDto> update(Long id,
                                        BankWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var optBankReadDto = Optional.ofNullable(
                    bankMapper.toReadDto(
                            bankRepository.update(session, id, bankMapper.toEntity(writeDto))
                                    .orElse(null)));
            session.getTransaction().commit();
            return optBankReadDto;
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
            var deleted = bankRepository.delete(session, id);
            session.getTransaction().commit();
            return deleted;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

}
