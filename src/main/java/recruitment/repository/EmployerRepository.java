package recruitment.repository;

import recruitment.mappers.EmployerMapper;
import recruitment.models.Applicant;
import recruitment.models.Employer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 25.05.2016.
 */
public class EmployerRepository extends PersonRepository {
    private static EmployerRepository instance;
            
    private EmployerMapper mapper;

    public static EmployerRepository getInstance() {
        if (instance == null) {
            instance = new EmployerRepository();
        }
        return instance;
    }
    
    private EmployerRepository() {
        super();
        mapper = new EmployerMapper();
    }

    @Override
    public Employer getByLoginAndPass(String login, String pass) {
        return mapper.getAll().stream()
                .filter(
                        m -> m.getLogin().equals(login) && m.getPassword().equals(pass)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public long save(Object o) {
        return mapper.save((Employer) o);
    }

    @Override
    public List<Employer> getAll() {
        return mapper.getAll();
    }

    @Override
    public Employer getById(int id) {
        return mapper.getById(id);
    }

    @Override
    public void update(Object o) {
        mapper.update((Employer)o);
    }

    @Override
    public void remove(int id) {
        mapper.delete(id);
    }
}
