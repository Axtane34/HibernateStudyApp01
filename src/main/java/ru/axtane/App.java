package ru.axtane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.Person;

public class App
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.save(new Person("Tom", 20));
            session.save(new Person("Bob", 22));
            session.save(new Person("Mitch", 21));

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }
}
