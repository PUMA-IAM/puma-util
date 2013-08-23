/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puma.util.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author jasper
 */
public class TransactionalWriter {
	private static Logger logger = Logger.getLogger(TransactionalWriter.class.getCanonicalName());
    private EntityTransaction userTransaction;
    private EntityManager entityManager;
    public TransactionalWriter(EntityManagerProducer producer) {             
            this.entityManager = producer.produce();
            this.userTransaction = this.entityManager.getTransaction();     
    }
    
    public <T extends Object> void customQuery(String namedQuery) {
    	try {
            this.userTransaction.begin();
            this.entityManager.createQuery(namedQuery).executeUpdate();
            this.userTransaction.commit();           
        } catch (SecurityException ex) {
        	logger.log(Level.SEVERE, "Could not execute custom query", ex);
        } catch (IllegalStateException ex) {
        	logger.log(Level.SEVERE, "Could not execute custom query", ex);
        } 
    }
    
    @SuppressWarnings("unchecked")
	public <T extends Object> void write(Class<T> objClass, Object object) {
        try {
            this.userTransaction.begin();
            this.entityManager.persist((T) object);
            this.userTransaction.commit();           
        } catch (SecurityException ex) {
        	logger.log(Level.SEVERE, "Could not perform write query", ex);
        } catch (IllegalStateException ex) {
        	logger.log(Level.SEVERE, "Could not perform write query", ex);
        } 
    }
    
     @SuppressWarnings("unchecked")
	public <T extends Object> void remove(Class<T> objClass, Object object) {
        try {
            this.userTransaction.begin();
            this.entityManager.remove((T) object);
            this.userTransaction.commit();                        
        } catch (SecurityException ex) {
        	logger.log(Level.SEVERE, "Could not perform remove query", ex);
        } catch (IllegalStateException ex) {
        	logger.log(Level.SEVERE, "Could not perform remove query", ex);
        }
    }
     
    @SuppressWarnings("unchecked")
	public <T extends Object> T merge(Class<T> objClass, Object object) {
        try {
            this.userTransaction.begin();
            T t = this.entityManager.merge((T) object);
            this.userTransaction.commit();                        
            return t;
        } catch (SecurityException ex) {
        	logger.log(Level.SEVERE, "Could not perform merge query", ex);
        } catch (IllegalStateException ex) {
        	logger.log(Level.SEVERE, "Could not perform merge query", ex);
        }
        return null;
    }
     
    public EntityTransaction getTransaction() {
        return this.userTransaction;
    }
}
