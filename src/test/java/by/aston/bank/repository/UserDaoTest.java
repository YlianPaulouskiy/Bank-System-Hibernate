package by.aston.bank.repository;

import by.aston.bank.model.Client;
import by.aston.bank.model.User;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTest {

    private final UserRepository userRepository = new UserRepository();
    private final static Long FIND_ID = 1L;
    private final static Integer SIZE = 3;
    private User createUser;
    private User rightUser;

    @BeforeEach
    void setUp() {
        TestDBUtils.initDb();
        createUser = new Client();
        createUser.setName("Mamkin");
        createUser.setLastName("Hacker");
        rightUser = new User();
        rightUser.setId(1L);
        rightUser.setName("Maxim");
        rightUser.setLastName("Yarosh");
    }

    @AfterAll
    static void tearDown() {
            TestDBUtils.getSession().close();
    }

    @Test
    public void findByIdTest() {
        var userOptional = userRepository.findById(TestDBUtils.getSession(), FIND_ID);
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getId(), rightUser.getId());
        assertEquals(userOptional.get().getName(), rightUser.getName());
        assertEquals(userOptional.get().getLastName(), rightUser.getLastName());
    }

    @Test
    public void findAllTest() {
        var users = userRepository.findAll(TestDBUtils.getSession());
        assertEquals(users.size(), SIZE);
    }

    @Test
    public void saveTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        var userOptional = userRepository.save(session, createUser);
        session.getTransaction().commit();
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getName(), createUser.getName());
        assertEquals(userOptional.get().getLastName(), createUser.getLastName());
        assertEquals(userRepository.findAll(session).size(), SIZE + 1);
    }

    @Test
    void updateTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        var userOptional = userRepository.update(session, 2L, createUser);
        session.getTransaction().commit();
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getName(), createUser.getName());
        assertEquals(userOptional.get().getLastName(), createUser.getLastName());
        assertEquals(userRepository.findAll(session).size(), SIZE);
    }

    @Test
    void deleteTest() {
        var session = TestDBUtils.getSession();
        session.beginTransaction();
        assertTrue(userRepository.delete(session, FIND_ID));
        session.getTransaction().commit();
        assertEquals(userRepository.findAll(session).size(), SIZE - 1);
    }
}
