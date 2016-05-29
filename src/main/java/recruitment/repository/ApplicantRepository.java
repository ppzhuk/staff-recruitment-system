package recruitment.repository;

import recruitment.models.Applicant;

import java.util.ArrayList;
import java.util.List;

public class ApplicantRepository extends PersonRepository {
    private static ApplicantRepository instance;

    private List<Applicant> list;

    public static ApplicantRepository getInstance() {
        if (instance == null) {
            instance = new ApplicantRepository();
        }
        return instance;
    }
    
    private  ApplicantRepository() {
        super();
        list = new ArrayList<>(3);
        super.getAll().stream()
                .filter(p -> p.getPersonId() >= 7 && p.getPersonId() < 10)
                .forEach(p -> list.add(new Applicant(list.size() + 1, p)));
    }

    @Override
    public Applicant getByLoginAndPass(String login, String pass) {
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
        Applicant a = (Applicant) o;
        a.setApplicantId(list.size()+1);
        return list.add(a);
    }

    @Override
    public List<Applicant> getAll() {
        return list;
    }

    @Override
    public Applicant getById(int id) {
        return list.get(id - 1);
    }
}
