package by.aston.bank.repository;

import by.aston.bank.model.BaseEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity> {

    Optional<E> findById(Session session, K id);

    List<E> findAll(Session session);

    Optional<E> save(Session session, E entity);

    Optional<E> update(Session session, K id, E entity);

    boolean delete(Session session, K id);

}
