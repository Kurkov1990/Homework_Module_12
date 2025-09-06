package app.dao.impl;

import app.config.HibernateUtils;
import app.dao.ClientDao;
import app.entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    @Override
    public Client create(Client entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            s.persist(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return Optional.ofNullable(s.find(Client.class, id));
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return s.createQuery("from Client", Client.class).list();
        }
    }

    @Override
    public Client update(Client entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            Client merged = s.merge(entity);
            tx.commit();
            return merged;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            Client found = s.find(Client.class, id);
            if (found != null) s.remove(found);
            tx.commit();
        }
    }
}
