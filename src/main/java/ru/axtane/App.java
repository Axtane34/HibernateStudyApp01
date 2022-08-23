package ru.axtane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.*;


public class App
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class).addAnnotatedClass(Item.class)
                .addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class)
                .addAnnotatedClass(Passport.class).addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class).addAnnotatedClass(Actor.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();


        try(sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();



            session.getTransaction().commit();
        }
    }
}
