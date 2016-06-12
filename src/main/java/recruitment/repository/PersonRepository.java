package recruitment.repository;

import recruitment.mappers.PersonMapper;
import recruitment.models.Manager;
import recruitment.models.Person;

import java.util.List;
import java.util.Optional;

public class PersonRepository implements BaseRepository {
    private static PersonRepository instance;
    
    private PersonMapper mapper;

    public static PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }
    
    protected PersonRepository() {
        mapper = new PersonMapper();

    }

    public Person getByLoginAndPass(String login, String pass) {
        Optional<Person> person =
                mapper.getAll().stream()
                        .filter(
                                p -> p.getLogin().equals(login) && p.getPassword().equals(pass)
                        )
                        .findFirst();

        return person.orElse(null);
    }

    @Override
    public long save(Object o) {
        return mapper.save((Person) o);
    }

    @Override
    public List<? extends Person> getAll() {
        return mapper.getAll();
    }

    @Override
    public Person getById(int id) {
        return mapper.getById(id);
    }

    @Override
    public void update(Object o) {
        mapper.update((Person)o);
    }

    @Override
    public void remove(int id) {
        mapper.delete(id);
    }
    
    public List<Person> getAllNonManagers() {
        return mapper.getAllNonManagers();
    }
}
