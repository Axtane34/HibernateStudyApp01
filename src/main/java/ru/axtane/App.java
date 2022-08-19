package ru.axtane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.Person;

import java.util.List;

public class App
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.createQuery("update Person set name='Test' where name like 'T%'").executeUpdate();
            session.createQuery("delete from Person where age<18").executeUpdate();
            List<Person> list = session.createSelectionQuery("From Person Where name Like 'T%'", Person.class).getResultList();
            List<Person> list1 = session.createSelectionQuery("From Person Where age<35", Person.class).getResultList();

            for (Person person : list1){
                System.out.println(person);
            }

            session.getTransaction().commit();

        }finally {
            sessionFactory.close();
        }
    }
}
