package org.springframework.samples.petclinic.system;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.visit.Visit;

public enum DataFactory {

    ORM();

    private final SessionFactory sessionFactory;
    private final HibernateSession hibernateSession;

    DataFactory () {
        sessionFactory = buildSessionFactory();
        hibernateSession = new HibernateSession() {
            @Override
            public SessionFactory getSessionFactory () {
                return sessionFactory;
            }
        };
    }

    public HibernateSession session () {
        return hibernateSession;
    }
    
    private SessionFactory buildSessionFactory () {
        try {
            // Try and build session factory from hibernate.cfg.xml file
            Configuration config
                    = new Configuration().configure("hibernate.cfg.xml");
            config.addAnnotatedClass(BaseEntity.class);
            config.addAnnotatedClass(Person.class);
            config.addAnnotatedClass(Owner.class);
            config.addAnnotatedClass(Vet.class);
            config.addAnnotatedClass(Specialty.class);
            config.addAnnotatedClass(Pet.class);
            config.addAnnotatedClass(PetType.class);
            config.addAnnotatedClass(Visit.class);
            config.addAnnotatedClass(NamedEntity.class);
            return config.buildSessionFactory(new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build());
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public interface HibernateSession {
        public SessionFactory getSessionFactory ();
    }

}
