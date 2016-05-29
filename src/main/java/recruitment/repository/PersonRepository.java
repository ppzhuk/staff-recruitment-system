package recruitment.repository;

import recruitment.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepository implements BaseRepository {
    private static PersonRepository instance;
    
    private List<Person> list;

    public static PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }
    
    protected PersonRepository() {
        list = new ArrayList<>(9);
        list.add(new Person(1, "Менеджер1 Менеджер Менеджер", "mngr1@mail.ru", "manager1", "manager1"));
        list.add(new Person(2, "Менеджер2 Менеджер Менеджер", "mngr2@mail.ru", "manager2", "manager2"));
        list.add(new Person(3, "Менеджер3 Менеджер Менеджер", "mngr3@mail.ru", "manager3", "manager3"));

        list.add(new Person(4, "Работодатель1 Работодатель Работодатель", "emplr1@mail.ru", "emplr1", "emplr1"));
        list.add(new Person(5, "Работодатель2 Работодатель Работодатель", "emplr2@mail.ru", "emplr2", "emplr2"));
        list.add(new Person(6, "Работодатель3 Работодатель Работодатель", "emplr3@mail.ru", "emplr3", "emplr3"));

        list.add(new Person(7, "Соискатель1 Соискатель Соискатель", "aplcnt1@mail.ru", "aplcnt1", "aplcnt1"));
        list.add(new Person(8, "Соискатель2 Соискатель Соискатель", "aplcnt2@mail.ru", "aplcnt2", "aplcnt2"));
        list.add(new Person(9, "Соискатель3 Соискатель Соискатель", "aplcnt3@mail.ru", "aplcnt3", "aplcnt3"));

    }

    public Person getByLoginAndPass(String login, String pass) {
        Optional<Person> person =
                list.stream()
                        .filter(
                                p -> p.getLogin().equals(login) && p.getPassword().equals(pass)
                        )
                        .findFirst();

        return person.orElse(null);
    }

    @Override
    public boolean save(Object o) {
        Person p = (Person) o;
        p.setPersonId(list.size()+1);
        return list.add(p);
    }

    @Override
    public List<? extends Person> getAll() {
        return list;
    }

    @Override
    public Person getById(int id) {
        return list.get(id - 1);
    }
}
