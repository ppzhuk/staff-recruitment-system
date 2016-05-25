package recruitment.repository;

import recruitment.models.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerRepository extends PersonRepository {
    private List<Manager> list;

    public ManagerRepository() {
        super();
        list = new ArrayList<>(3);
        super.getAll().stream()
                .filter(p -> p.getPersonId() < 4)
                .forEach(p -> list.add(new Manager(list.size() + 1, p)));
    }

    @Override
    public Manager getByLoginAndPass(String login, String pass) {
        return list.stream()
                .filter(
                        m -> m.getLogin().equals(login) && m.getPassword().equals(pass)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Object o) {
        super.save(o);
        list.add((Manager) o);
    }

    @Override
    public List<Manager> getAll() {
        return list;
    }

    @Override
    public Manager getById(int id) {
        return list.get(id - 1);
    }
}
