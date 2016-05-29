package recruitment.repository;

import recruitment.models.Employer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 25.05.2016.
 */
public class EmployerRepository extends PersonRepository {
    private static EmployerRepository instance;
            
    private List<Employer> list;

    public static EmployerRepository getInstance() {
        if (instance == null) {
            instance = new EmployerRepository();
        }
        return instance;
    }
    
    private EmployerRepository() {
        super();
        list = new ArrayList<>(3);
        super.getAll().stream()
                .filter(p ->
                        p.getPersonId() >= 4 && p.getPersonId() < 7)
                .forEach(p -> {
                    Employer e = new Employer(list.size() + 1, p);
                    e.setCompanyName("Company" + e.getEmployerId());
                    e.setDescription("The " + e.getEmployerId() + " best company in the world!");
                    e.setSite("www.company" + e.getEmployerId() + ".org");
                    list.add(e);
                });
    }

    @Override
    public Employer getByLoginAndPass(String login, String pass) {
        return list.stream()
                .filter(
                        m -> m.getLogin().equals(login) && m.getPassword().equals(pass)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean save(Object o) {
        super.save(o);
        Employer e = (Employer) o;
        e.setEmployerId(list.size()+1);
        return list.add(e);
    }

    @Override
    public List<Employer> getAll() {
        return list;
    }

    @Override
    public Employer getById(int id) {
        return list.get(id - 1);
    }
}
