package recruitment.repository;

import recruitment.mappers.ManagerMapper;
import recruitment.models.Employer;
import recruitment.models.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerRepository extends PersonRepository {
    private static ManagerRepository instance;
    
    private ManagerMapper mapper;

    public static ManagerRepository getInstance() {
        if (instance == null) {
            instance = new ManagerRepository();
        }
        return instance;
    }
    
    private  ManagerRepository() {
        super();
        mapper = new ManagerMapper();
    }

    @Override
    public Manager getByLoginAndPass(String login, String pass) {
        return mapper.getAll().stream()
                .filter(
                        m -> m.getLogin().equals(login) && m.getPassword().equals(pass)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public long save(Object o) {
        return mapper.save((Manager) o);
    }

    @Override
    public List<Manager> getAll() {
        return mapper.getAll();
    }

    @Override
    public Manager getById(int id) {
        return  mapper.getById(id);
    }

    @Override
    public void update(Object o) {
        mapper.update((Manager)o);
    }

    @Override
    public void remove(int id) {
        mapper.delete(id);
    }
}
