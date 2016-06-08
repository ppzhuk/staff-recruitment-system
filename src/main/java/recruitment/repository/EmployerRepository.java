package recruitment.repository;

import recruitment.mappers.EmployerMapper;
import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Manager;
import recruitment.models.Person;

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
    
    public long save(String name, String login, String password, String email, 
                     String companyName, String description, String site) {
        Person p = new Person(
                name,
                email,
                login,
                password
        );
        Employer empl = new Employer(0, p);
        empl.setCompanyName(companyName);
        empl.setDescription(description);
        empl.setSite(site);
        
        return save(empl);
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

    public Employer getByPersonId(int personId) {
        return mapper.getAll()
                .stream()
                .filter( m -> m.getPersonId() == personId)
                .findFirst()
                .orElse(null);
    }
}
