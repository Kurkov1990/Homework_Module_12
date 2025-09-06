package app.dao.impl;

import app.config.HibernateUtils;
import app.dao.PlanetDao;
import app.entity.Planet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PlanetDaoImpl implements PlanetDao {

    @Override
    public Planet create(Planet entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            s.persist(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public Optional<Planet> findById(String id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return Optional.ofNullable(s.get(Planet.class, id));
        }
    }

    @Override
    public List<Planet> findAll() {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return s.createQuery("from Planet", Planet.class).list();
        }
    }

    @Override
    public Planet update(Planet entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            Planet merged = s.merge(entity);
            tx.commit();
            return merged;
        }
    }

    @Override
    public void deleteById(String id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();
            Planet found = s.find(Planet.class, id);
            if (found != null) s.remove(found);
            tx.commit();
        }
    }
}
