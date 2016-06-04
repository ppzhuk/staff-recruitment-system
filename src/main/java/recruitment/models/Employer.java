package recruitment.models;

import recruitment.repository.EntityRepository;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Employer extends Person {
    private int id;
    private String companyName;
    private String description;
    private String site;

    public Employer(String name, String email, String login, String password, String companyName) {
        super(name, email, login, password);
        this.companyName = companyName;
    }

    public Employer(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getEmployerId() {
        return id;
    }

    public void setEmployerId(int id) {
        this.id = id;
    }

    public long createVacancy(int employerId, String position, String requirements, double salary) {
        EntityRepository repo = EntityRepository.getInstance();
        return repo.save(new Vacancy(employerId, position, requirements, salary));
    }

    public void closeVacancy(int vacancyId, int applicantId) {
        EntityRepository repo = EntityRepository.getInstance();
        Vacancy v = (Vacancy) repo.getById(vacancyId, EntityRepository.VACANCY_TYPE);
        v.setStatus(Vacancy.STATUS_CLOSE, applicantId);
        repo.update(v, EntityRepository.VACANCY_TYPE);
    }
}
