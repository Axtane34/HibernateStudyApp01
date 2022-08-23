package ru.axtane;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

            Person person = session.get(Person.class, 2);
            System.out.println("Получили человека");

            //Получим связанные сущности (Lazy) - 2 запроса
            //Hibernate.initialize(person.getItems()); //подгружаем связанные Lazy сущности

            /*Item item = session.get(Item.class, 3);
            System.out.println("Получили товар");

            //Получим связанные сущности (Eager) - 1 запрос\left join
            System.out.println(item.getOwner());*/

            session.getTransaction().commit();

            System.out.println("Сессия завершилась (session.close)");

            System.out.println("Вне сессии");

            //открываем сессию и транзакцию еще раз, можно сделать в любом месте кода
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Внутри второй транзакции");

            //нужно делать только в первом случае, при hql запросе в этом нет нужды
            person = (Person) session.merge(person);
            //первый вариант, предпочтительный
            //Hibernate.initialize(person.getItems());
            //второй вариант, избыточный
            List<Item> items = session.createSelectionQuery("select i from Item i where i.owner.id=:person_id", Item.class)
                    .setParameter("person_id", person.getId()).getResultList();
            System.out.println(items);
            session.getTransaction().commit();

            System.out.println("Вне второй сессии");
            //это работает так как связанные товары были загружены
            //System.out.println(person.getItems());

            //после указания fetch = FetchType.EAGER даже в состоянии detach можно получить товары по геттеру объекта
            //System.out.println(person.getItems());
        }
    }
}
