package ru.axtane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.axtane.model.Item;
import ru.axtane.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

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

            session.getTransaction().commit();

        }finally {
            sessionFactory.close();
        }
    }
}
