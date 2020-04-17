package com.daos.repu;

import com.daos.DAO;
import com.entities.ObjectEntity;
import com.util.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepusitory<T extends ObjectEntity> implements DAO<T> {

    Class<T> objectsClass;

    public final String getClassName(){
        return objectsClass.getName();
    }

    @Override
    public long count() {
        return PersistenceUtil.getEntityManager().createQuery(
                "select count(*) from " + objectsClass.getName(),
                Long.class
        ).getSingleResult();
    }

    @Override
    public Integer getNextId() {
        return PersistenceUtil.getEntityManager().createQuery(
                "select max(a.id) from " + objectsClass.getName() + " a",
                Integer.class
        ).getSingleResult() + 1;
    }

    @Override
    public void deleteById(Integer id) {

        EntityManager em = PersistenceUtil.getEntityManager();

        var entities = em.createQuery("select a from " + objectsClass.getName() + " a where a.id = :id",
                objectsClass
        ).setParameter("id",id).getResultList();

        if(entities.isEmpty()){
            System.out.println("The " + getClassName() + " with id = " + id + " does not exist.");
        }
        else {
            em.getTransaction().begin();

            em.remove(entities.get(0));

            em.getTransaction().commit();
            em.close();
        }


    }

    @Override
    public List<T> findAll() {
        return PersistenceUtil.getEntityManager().createQuery(
                "select a from " + getClassName() + " a",
                objectsClass
        ).getResultList();
    }

    @Override
    public Optional<T> findById(Integer id) {
        T t = PersistenceUtil.getEntityManager().createQuery(
                "select a from " + getClassName() + " a where a.id = :id",
                objectsClass
        ).setParameter("id",id)
        .getSingleResult();

        return Optional.ofNullable(t);
    }

    @Override
    public <S extends T> S save(S entity) {
        EntityManager  em = PersistenceUtil.getEntityManager();

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        return entity;
    }

}
