package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static org.springframework.samples.petclinic.system.DataFactory.ORM;

public class OwnerDAO {

    public static final String TABLE_NAME = "owners";
    
    public Boolean save(Owner entity) {
        Boolean result = false;
        Session session = ORM.session().getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();
            session.persist(entity);
            txn.commit();
            result = true;
        } catch (Exception ex) {
            if (txn != null) {
                txn.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    public Owner findById(Integer id) {
        Owner entity = null;
        Session session = ORM.session().getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();
            entity = session.get(Owner.class, id);
            txn.commit();
        } catch (HibernateException ex) {
            if (txn != null) {
                txn.rollback();
            }
        } finally {
            session.close();
        }
        return entity;
    }
    
    public List<Owner> findByLastName(String lastName) {
        List<Owner> entities = new ArrayList<>();
        Session session = ORM.session().getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();
            Query query = session.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName = '" + lastName + "'");
            entities = query.list();
            txn.commit();
        } catch (HibernateException ex) {
            if (txn != null) {
                txn.rollback();
            }
        } finally {
            session.close();
        }
        return entities;
    }  
    
//    public List<Owner> findByLastName(String lastName) {
//        List<Owner> entities = new ArrayList<>();
//        Session session = ORM.session().getSessionFactory().openSession();
//        Transaction txn = session.getTransaction();
//        try {
//            txn.begin();
//            Query query = session.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE '%" + lastName + "%'");
//            entities = query.list();
//            txn.commit();
//        } catch (HibernateException ex) {
//            if (txn != null) {
//                txn.rollback();
//            }
//        } finally {
//            session.close();
//        }
//        return entities;
//    }
    
}
