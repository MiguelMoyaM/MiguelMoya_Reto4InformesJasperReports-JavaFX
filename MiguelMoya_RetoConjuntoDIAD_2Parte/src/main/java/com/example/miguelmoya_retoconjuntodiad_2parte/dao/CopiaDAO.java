package com.example.miguelmoya_retoconjuntodiad_2parte.dao;

import com.example.miguelmoya_retoconjuntodiad_2parte.model.Copia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia>{
    private SessionFactory sessionFactory;

    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Copia> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select c from Copia c", Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<Copia>(0);
        }
    }

    @Override
    public Copia findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Copia.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction( session -> session.persist(copia) );
    }

    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction( session -> session.merge(copia) );
    }

    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction( session -> session.remove(copia) );
    }

    /***
     *
     * @param userId id del usuario del que quieras recibir todas sus copias
     * @return
     */
    public List<Copia> findByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Copia c WHERE c.idUsuario = :userId";
            return session.createQuery(hql, Copia.class)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Copia> findCopiasByPeliculaId(Long peliculaId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Copia WHERE idPelicula = :idPelicula";
            Query<Copia> query = session.createQuery(hql, Copia.class);
            query.setParameter("idPelicula", peliculaId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    public void deleteAllCopias() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Copia").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
