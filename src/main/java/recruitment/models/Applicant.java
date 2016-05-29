package recruitment.models;

import recruitment.repository.EntityRepository;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Applicant extends Person {
    EntityRepository repo = EntityRepository.getInstance();
    
    private int id;

    public Applicant(String name, String email, String login, String password) {
        super(name, email, login, password);
    }

    public Applicant(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());
        this.id = id;
    }
    
    public int getApplicantId() {
        return id;
    }

    public void setApplicantId(int id) {
        this.id = id;
    }
    
    public boolean createResume(int applicantId, String experience, String skills, String education) {
        return repo.save(new Resume(applicantId, experience, skills, education));
    }
    
    public void setFoundJob(int employerVacancyId) {
        Resume r = getResume();
        if (r == null) {
            throw new NullPointerException("Resume not found.");
        }
        r.setInSearch(false, employerVacancyId);
    }
    
    public Resume getResume() {
        return (Resume) repo.getAll(EntityRepository.RESUME_TYPE)
                .stream()
                .filter(o -> ((Resume)o).getApplicantId() == id)
                .findFirst()
                .orElse(null);
    }
    
}
