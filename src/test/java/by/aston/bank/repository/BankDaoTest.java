package by.aston.bank.repository;

import by.aston.bank.model.Bank;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankDaoTest {

    private final BankRepository bankRepository = new BankRepository();
    private final static Long FIND_ID = 1L;
    private final static Integer SIZE = 4;
    private Bank created;


    @BeforeEach
    void setUp() {
        TestDBUtils.initDb();
        created = new Bank();
        created.setTitle("World Bank");
    }

    @AfterAll
    static void tearDown() {
        TestDBUtils.getSession().close();
    }

    @Test
    public void findByIdTest() {
        var optional = bankRepository.findById(TestDBUtils.getSession(), FIND_ID);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getId(), FIND_ID);
        assertEquals(optional.get().getTitle(), "Bank of America");
    }

    @Test
    public void findAllTest() {
        var entities = bankRepository.findAll(TestDBUtils.getSession());
        assertEquals(entities.size(), SIZE);
    }

    @Test
    public void saveTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        var optional = bankRepository.save(session, created);
        session.getTransaction().commit();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getTitle(), created.getTitle());
        assertEquals(bankRepository.findAll(session).size(), SIZE + 1);
    }

    @Test
    void updateTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        var optional = bankRepository.update(session, 2L, created);
        session.getTransaction().commit();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getTitle(), created.getTitle());
        assertEquals(bankRepository.findAll(session).size(), SIZE);
    }

    @Test
    void deleteTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        assertTrue(bankRepository.delete(session, 4L));
        session.getTransaction().commit();
        assertEquals(bankRepository.findAll(session).size(), SIZE - 1);
    }

}
