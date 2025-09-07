package app.dao.impl;

import app.config.HibernateUtils;
import app.dao.ClientDao;
import app.entity.Client;
import app.exception.DaoException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    @Override
    public Client create(Client entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                s.persist(entity);
                s.getTransaction().commit();
                return entity;
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to create Client", e);
            }
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return Optional.ofNullable(s.find(Client.class, id));
        } catch (Exception e) {
            throw new DaoException("Failed to find Client by id=" + id, e);
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return s.createQuery("from Client", Client.class).list();
        } catch (Exception e) {
            throw new DaoException("Failed to load all Clients", e);
        }
    }

    @Override
    public Client update(Client entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                Client merged = s.merge(entity);
                s.getTransaction().commit();
                return merged;
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to update Client id=" + entity.getId(), e);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                Client found = s.find(Client.class, id);
                if (found != null) s.remove(found);
                s.getTransaction().commit();
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to delete Client id=" + id, e);
            }
        }
    }
}
