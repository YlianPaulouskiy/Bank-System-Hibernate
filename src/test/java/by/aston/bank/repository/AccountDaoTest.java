package by.aston.bank.repository;

import by.aston.bank.model.Account;
import by.aston.bank.model.Bank;
import by.aston.bank.model.User;
import by.aston.bank.utils.TestDBUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountDaoTest {

    private final AccountRepository accountRepository = new AccountRepository();
    private final static Long FIND_ID = 1L;
    private final static Integer SIZE = 9;
    private Account created;


    @BeforeEach
    void setUp() {
        TestDBUtils.initDb();
        created = new Account();
        created.setNumber("1111112332");
        created.setCash(new BigDecimal("200.00"));
        var user = new User();
        user.setId(1L);
        var bank = new Bank();
        bank.setId(1L);
        created.setUser(user);
        created.setBank(bank);
    }

    @AfterAll
    static void tearDown() {
        TestDBUtils.getSession().close();
    }

    @Test
    public void findByIdTest() {
        var optional = accountRepository.findById(TestDBUtils.getSession(), FIND_ID);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getId(), FIND_ID);
        assertEquals(optional.get().getNumber(), "12376589567");
        assertEquals(optional.get().getCash(), new BigDecimal("70.77"));
    }

    @Test
    public void findAllTest() {
        var entities = accountRepository.findAll(TestDBUtils.getSession());
        assertEquals(entities.size(), SIZE);
    }

    @Test
    public void saveTest() {
        var optional = accountRepository.save(TestDBUtils.getSession(), created);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getNumber(), created.getNumber());
        assertEquals(optional.get().getCash(), created.getCash());
        assertEquals(accountRepository.findAll(TestDBUtils.getSession()).size(), SIZE + 1);
    }

    @Test
    void updateTest() {
        var optional = accountRepository.update(TestDBUtils.getSession(), 5L, created);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getNumber(), created.getNumber());
        assertEquals(optional.get().getCash(), created.getCash());
        assertEquals(accountRepository.findAll(TestDBUtils.getSession()).size(), SIZE);
    }

    @Test
    void deleteTest() {
        TestDBUtils.getSession().beginTransaction();
        assertTrue(accountRepository.delete(TestDBUtils.getSession(), FIND_ID));
        assertEquals(accountRepository.findAll(TestDBUtils.getSession()).size(), SIZE - 1);
        TestDBUtils.getSession().getTransaction().commit();
    }

}
