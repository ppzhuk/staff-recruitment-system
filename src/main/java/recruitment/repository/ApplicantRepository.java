package recruitment.repository;

import recruitment.mappers.ApplicantMapper;
import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Manager;
import recruitment.models.Person;

import java.util.ArrayList;
import java.util.List;

public class ApplicantRepository extends PersonRepository {
    private static ApplicantRepository instance;

    private ApplicantMapper mapper;

    public static ApplicantRepository getInstance() {
        if (instance == null) {
            instance = new ApplicantRepository();
        }
        return instance;
    }
    
    private  ApplicantRepository() {
        super();
        mapper = new ApplicantMapper();
    }

    @Override
    public Applicant getByLoginAndPass(String login, String pass) {
        return mapper.getAll().stream()
                .filter(
                        m -> m.getLogin().equals(login) && m.getPassword().equals(pass)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public long save(Object o) {
        return mapper.save((Applicant) o);
    }

    public long save(String name, String login, String password, String email) {
        Person p = new Person(
                name,
                email,
                login,
                password
        );
        Applicant applicant = new Applicant(-1, p);
        return save(applicant);
    }
    
    @Override
    public List<Applicant> getAll() {
        return mapper.getAll();
    }

    @Override
    public Applicant getById(int id) {
        return mapper.getById(id);
    }

    @Override
    public void update(Object o) {
        mapper.update((Applicant)o);
    }

    @Override
    public void remove(int id) {
        mapper.delete(id);
    }

    public Applicant getByPersonId(int personId) {
        return mapper.getAll()
                .stream()
                .filter( m -> m.getPersonId() == personId)
                .findFirst()
                .orElse(null);
    }
}
