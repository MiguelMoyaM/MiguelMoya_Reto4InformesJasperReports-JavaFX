package com.example.miguelmoya_retoconjuntodiad_2parte.dao;

import com.example.miguelmoya_retoconjuntodiad_2parte.model.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO implements DAO<Pelicula> {
    private SessionFactory sessionFactory;

    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Pelicula> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select p from Pelicula p",Pelicula.class).list();
        } catch (Exception e) {
            return new ArrayList<Pelicula>(0);
        }
    }

    @Override
    public Pelicula findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Pelicula.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction( session -> session.persist(pelicula) );
    }

    @Override
    public void update(Pelicula pelicula) {
        sessionFactory.inTransaction( session -> session.merge(pelicula) );
    }

    @Override
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction( session -> session.remove(pelicula) );
    }

    public Pelicula findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT * FROM pelicula WHERE titulo = :name";
            return session.createNativeQuery(sql, Pelicula.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteAllPeliculas() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Pelicula").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
