package app.dao.impl;

import app.config.HibernateUtils;
import app.dao.PlanetDao;
import app.entity.Planet;
import app.exception.DaoException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PlanetDaoImpl implements PlanetDao {

    @Override
    public Planet create(Planet entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                s.persist(entity);
                s.getTransaction().commit();
                return entity;
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to create Planet", e);
            }
        }
    }

    @Override
    public Optional<Planet> findById(String id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return Optional.ofNullable(s.get(Planet.class, id));
        } catch (Exception e) {
            throw new DaoException("Failed to find Planet by id=" + id, e);
        }
    }

    @Override
    public List<Planet> findAll() {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return s.createQuery("from Planet", Planet.class).list();
        } catch (Exception e) {
            throw new DaoException("Failed to load all Planets", e);
        }
    }

    @Override
    public Planet update(Planet entity) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                Planet merged = s.merge(entity);
                s.getTransaction().commit();
                return merged;
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to update Planet id=" + entity.getId(), e);
            }
        }
    }

    @Override
    public void deleteById(String id) {
        try (Session s = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            s.beginTransaction();
            try {
                Planet found = s.find(Planet.class, id);
                if (found != null) s.remove(found);
                s.getTransaction().commit();
            } catch (Exception e) {
                if (s.getTransaction().isActive()) s.getTransaction().rollback();
                throw new DaoException("Failed to delete Planet id=" + id, e);
            }
        }
    }
}
