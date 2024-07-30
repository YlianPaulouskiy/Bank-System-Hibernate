package by.aston.bank.repository;

import by.aston.bank.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;

    @Override
    public Optional<E> findById(Session session, K id) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll(Session session) {
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();

    }

    @Override
    public Optional<E> save(Session session, E entity) {
        session.save(entity);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<E> update(Session session, K id, E entity) {
        entity.setId((K) id);
        session.merge(entity);
        return Optional.of(entity);
    }

    @Override
    public boolean delete(Session session, K id) {
        var optional = findById(session, id);
        if (optional.isEmpty()) return false;
        session.remove(optional.get());
        session.flush();
        return true;
    }
}
