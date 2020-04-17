package com.daos.repo;

import com.daos.DAO;
import com.entities.*;
import com.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends ObjectEntity> implements DAO<T> {

    Class<T> objectsClass;

    public final String getClassName(){
        return objectsClass.getName();
    }

    public static AbstractRepository getInstanceFor(Class<? extends ObjectEntity> objectsClass){
        if(objectsClass == Artist.class){
            return new ArtistsRepository();
        }
        if(objectsClass == Album.class){
            return new AlbumsRepository();
        }
        if(objectsClass == Chart.class){
            return new ChartsRepository();
        }
        if(objectsClass == ClasamentEntry.class){
            return new ClasamentsRepository();
        }
        if(objectsClass == Genre.class){
            return new GenreRepository();
        }

        throw new IllegalStateException("Unexpected value: " + objectsClass);

    }

    /**
     * @return the number of entities available.
     */
    public long count(){
        return HibernateUtil.getSession().createQuery("select count(*) from " + objectsClass.getName(),Long.class).getSingleResult();
    }

    public Integer getNextId(){
        return HibernateUtil.getSession().createQuery("select max(a.id) from " + objectsClass.getName() + " a",Integer.class).getSingleResult() + 1;
    }

    @Override
    public void deleteById(Integer id) {
        var session = HibernateUtil.getSession();

        List<T> ts = HibernateUtil.getSession().createQuery(
                "select a from " + objectsClass.getName() + " a where a.id = :id",
                objectsClass
        ).setParameter("id",id).getResultList();

        if(ts.isEmpty()){
            System.out.println("The " + getClassName() + " with id = " + id + " does not exist.");
        }
        else {
            session.beginTransaction();
            session.delete(ts.get(0));
            session.getTransaction().commit();
            session.close();
        }
    }

    public boolean existsById(Integer id){
        return findById(id).isPresent();
    }
    //Returns whether an entity with the given id exists.

    public List<T> findAll(){
        return HibernateUtil.getSession().createQuery("select a from " + getClassName() + " a",objectsClass).getResultList();
    }
    //Returns all instances of the type.

    public List<T> findAllById(Iterable<Integer> ids){
        List<T> results = new ArrayList<>();

        for(var id : ids){
            findById(id).ifPresent(results::add);
        }

        return results;
    }
    //Returns all instances of the type T with the given IDs.

    public Optional<T> findById(Integer id){
        List<T> ts = HibernateUtil.getSession().createQuery("select a from " + objectsClass.getName() + " a where a.id = :id",objectsClass)
                .setParameter("id",id).getResultList();

        return Optional.ofNullable(ts.isEmpty()?null:ts.get(0));
    }
    //Retrieves an entity by its id.

    public <S extends T> S save(S entity){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();

        session.close();

        return entity;
    }


}
