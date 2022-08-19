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

            /*Person person = session.get(Person.class, 2);
            person.setName("April");
            person.setAge(27);
            session.remove(person);*/

            Person person = new Person("Roxane", 16);
            session.persist(person);

            session.getTransaction().commit();

            System.out.println(person.getId());
        }finally {
            sessionFactory.close();
        }
    }
}
