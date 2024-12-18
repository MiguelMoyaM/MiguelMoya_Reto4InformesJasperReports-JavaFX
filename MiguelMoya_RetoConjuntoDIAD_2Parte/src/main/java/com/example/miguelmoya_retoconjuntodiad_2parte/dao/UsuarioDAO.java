package com.example.miguelmoya_retoconjuntodiad_2parte.dao;

import com.example.miguelmoya_retoconjuntodiad_2parte.model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    private SessionFactory sessionFactory;

    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Usuario> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from Usuario u",Usuario.class).list();
        } catch (Exception e) {
            return new ArrayList<Usuario>(0);
        }
    }

    @Override
    public Usuario findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction( session -> session.persist(usuario) );
    }

    @Override
    public void update(Usuario usuario) {
        sessionFactory.inTransaction( session -> session.merge(usuario) );
    }

    @Override
    public void delete(Usuario usuario) {
        sessionFactory.inTransaction( session -> session.remove(usuario) );
    }

    public Usuario findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT * FROM usuario WHERE nombre = :name";
            return session.createNativeQuery(sql, Usuario.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Usuario").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
