package ru.axtane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.Director;
import ru.axtane.model.Item;
import ru.axtane.model.Movie;
import ru.axtane.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class).addAnnotatedClass(Item.class)
                .addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            //получение внешних объектов по внутреннему
           /* Person person = session.get(Person.class, 3);
            for (Item item : person.getItems()){
                System.out.println(item);
            }
            //получение внутреннего объекта по внешнему
            Item item = session.get(Item.class, 5);
            System.out.println(item.getOwner());

            //инъекция внешнего объекта во внутренний
            Person person1 = session.get(Person.class, 2);
            Item newItem = new Item("Item from hibernate", person1);
            //необязательное, но желательное взаимное покрытие, поскольку hibernate кеширует объекты и не всегда делает sql запросы заново
            person1.getItems().add(newItem); //кеширует hibernate объекты
            session.persist(newItem);*/

            //сохранение цепочки связанных объектов
            /*Person person = new Person("Quentin", 44);
            Item newItem = new Item("Placeholder", person);
            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));
            session.persist(person);
            session.persist(newItem);
            */

            //удаление из таблицы всех внешних объектов, внешний ключ которых указывает на внутренний объект
            /*Person person = session.get(Person.class, 3);
            List<Item> itemList = person.getItems();
            //SQL
            for (Item item : itemList){
                session.remove(item);
            }
            //Не порождает SQL, но важно для соответствия кеша таблице
            person.getItems().clear();*/

            //При удалении внутреннего объекта, происходит каскадирование on cascade set null
            /*Person person = session.get(Person.class, 2);
            session.remove(person);
            //для состояния Hibernate кеша
            person.getItems().forEach(i -> i.setOwner(null));*/

            //назначаем внешний объект внутреннему для таблицы
            /*Person person = session.get(Person.class, 4);
            Item item = session.get(Item.class, 1);
            //удаляем итем из списка старого владельца
            item.getOwner().getItems().remove(item);
            item.setOwner(person);
            //внутренний внешнему для кеша
            person.getItems().add(item);*/

           /* Director director = session.get(Director.class, 1);
            for (Movie movie : director.getMovies()){
                System.out.println(movie.getDir().getName() + ", " + movie.getName() + ", " + movie.getYearOfProduction());
            }*/

            /*Movie movie = session.get(Movie.class, 4);
            System.out.println(movie.getDir().getName() + ": " + movie.getName());*/

            /*Director director = session.get(Director.class, 3);
            Movie movie = new Movie("Aladdin", 2018, director);
            session.persist(movie);
            director.getMovies().add(movie);*/
            /*for (Movie movie : director.getMovies()){
                System.out.println(movie.getDir().getName() + ", " + movie.getName() + ", " + movie.getYearOfProduction());
            }*/

            /*Director director = new Director("Alfred Hitchcock", 1899, new ArrayList<>());
            Movie movie = new Movie("Psycho", 1998, director);
            session.persist(director);
            session.persist(movie);*/

            /*Movie aladdin = session.get(Movie.class, 12);
            Director richie = aladdin.getDir();
            richie.getMovies().remove(aladdin);

            Director hitchcock = session.get(Director.class, 7);
            aladdin.setDir(hitchcock);
            hitchcock.getMovies().add(aladdin);*/

            /*Director richie = session.get(Director.class, 3);
            richie.getMovies().stream().map(Movie::getName).forEach(System.out::println);*/


            session.getTransaction().commit();


        }finally {
            sessionFactory.close();
        }
    }
}
