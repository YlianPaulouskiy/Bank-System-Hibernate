package by.aston.bank.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@UtilityClass
public class TestDBUtils {

    private final Path schemaPath = Paths.get("src/test/resources/sql/schema.sql");
    private final Path dataPath = Paths.get("src/test/resources/sql/data.sql");
    private final Session session = HibernateUtils.open();

    public void initDb() {
        try {
            session.beginTransaction();
            session.createSQLQuery(FileUtils.read(schemaPath)).executeUpdate();
            session.createSQLQuery(FileUtils.read(dataPath)).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public Session getSession() {
        return session;
    }

}
