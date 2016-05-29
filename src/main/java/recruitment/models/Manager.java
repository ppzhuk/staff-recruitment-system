package recruitment.models;

import recruitment.repository.EntityRepository;
import recruitment.repository.PersonRepository;

import java.util.Date;
import java.util.List;

public class Manager extends Person {
    private int id;

    public Manager(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());  
        this.id = id;
    }

    public Manager(int id, String name, String email, String login, String password) {
        super(name, email, login, password);
        this.id = id;
    }
    
    
    public int getManagerId() {
        return id;
    }

    public void setManagerId(int id) {
        this.id = id;
    }
    
    public Vacancy getVacancy(int id) {
        EntityRepository repo = EntityRepository.getInstance();
        return (Vacancy) repo.getById(id, EntityRepository.VACANCY_TYPE);
    }
    
    public double getPersonMark(int id) {
        PersonRepository repo = PersonRepository.getInstance();
        Person p = repo.getById(id);
        return p.getAverageMark();
    }

    public Resume getResume(int id) {
        EntityRepository repo = EntityRepository.getInstance();
        return (Resume) repo.getById(id, EntityRepository.RESUME_TYPE);
    }
    
    public boolean createInterview(int applicantId, int vacancyId, Date interviewDate) {
        EntityRepository repo = EntityRepository.getInstance();
        return repo.save(new Interview(applicantId, vacancyId, interviewDate));
    }
    
    public boolean createMark(int mark, int personId) {
        EntityRepository repo = EntityRepository.getInstance();
        return repo.save(new Mark(id, personId, mark));
    }

    public void deleteMark(int markId) {
        EntityRepository repo = EntityRepository.getInstance();
        Mark mark = (Mark) repo.getById(markId, EntityRepository.MARK_TYPE);
        if (mark.getManagerId() == id) {
            repo.remove(markId, EntityRepository.MARK_TYPE);
            return;
        }
        throw new IllegalArgumentException("Manager with id " + id + "is not owner of this mark.");
    }
}
