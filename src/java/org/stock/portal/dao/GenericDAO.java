package org.stock.portal.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class GenericDAO<T, ID extends Serializable>{

    private static final Logger LOG = Logger.getLogger( GenericDAO.class.getName() );
    
    protected EntityManager em;
    
   
    public GenericDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    /** 
     * This method to persist an entity into DB {@inheritDoc}
     * 
     * @param t
     *            {@inheritDoc}
     */		
    public T create(T t) throws Exception{
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    /**
     * This method to delete a record from a table by ID {@inheritDoc}
     * 
     * @param type
     *            {@inheritDoc}
     * @param id
     *            {@inheritDoc}
     * */	
    public void delete(Class<T> type, ID id) {
        Object ref = (Object) this.em.getReference(type, id);
        this.em.remove(ref);
    }

    /**
     * This method to update a record
     * 
     * @param t
     * @return
     */	
    public T update(T t) throws Exception{
        this.em.merge(t);
        return t;
    }

    /**
     * This method to find a record from a table by passing the ID {@inheritDoc}
     * 
     * @param type
     *            {@inheritDoc}
     * @param id
     *            {@inheritDoc}
     * @return {@inheritDoc}
     */	
    public T find(Class<T> type, ID id) {
        //get the result as usual
        T result = this.em.find(type, id);
        return result;
    }		

    /**
     * This method to find all records from table {@inheritDoc}
     * 
     * @param t
     *            {@inheritDoc}
     * @return {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(Class<T> t) {
        String queryStr = "select o from " + t.getSimpleName() + " o";
        List<T> resultList = this.em.createQuery(queryStr).getResultList();
        return resultList;
    }
   
}