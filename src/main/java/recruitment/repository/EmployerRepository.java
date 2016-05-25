package recruitment.repository;

import recruitment.models.Employer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 25.05.2016.
 */
public class EmployerRepository extends PersonRepository {
    private List<Employer> list;

    public EmployerRepository() {
        super();
        list = new ArrayList<>(3);
        super.getAll().stream()
                .filter(p ->
                        p.getPersonId() >= 4 && p.getPersonId() < 7)
                .forEach(p -> {
                    Employer e = new Employer(list.size() + 1, p);
                    e.setCompanyName("Company" + e.getEmployerId());
                    e.setDescription("The " + e.getEmployerId() + " best company in the world!");
                    e.setCite("www.company" + e.getEmployerId() + ".org");
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
    public void save(Object o) {
        super.save(o);
        list.add((Employer) o);
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
