package recruitment.facade;

import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;
import recruitment.repository.PersonRepository;

/**
 * Created by Zhuk Pavel on 06.06.2016.
 */
public class Facade {
    
    public Vacancy getVacancy(int vacancyId) {
        return (Vacancy)
                EntityRepository.getInstance().getById((vacancyId), EntityRepository.VACANCY_TYPE);
    }
    
    public int getEmployerIdByPersonId(int personId) {
        return EmployerRepository.getInstance()
                .getByPersonId(personId)
                .getEmployerId();
    }
    
    public int getPersonIdByEmployerId(int employerId) {
        return EmployerRepository.getInstance().getById(employerId).getPersonId();
    }
    
    public Person getPerson(int employerPersonId) {
        return PersonRepository.getInstance().getById(employerPersonId); 
    }
    
    public void deleteVacancy(int vacancyId) {
        EntityRepository.getInstance().remove(vacancyId, EntityRepository.VACANCY_TYPE);
    }
        
    public void updateVacancy(Vacancy vacancy, String position, double salary, String requirements) {
        vacancy.setPosition(position);
        vacancy.setSalary(salary);
        vacancy.setRequirements(requirements);
        EntityRepository.getInstance().update(vacancy, EntityRepository.VACANCY_TYPE);
    }
    
    public void openVacancy(Vacancy vacancy) {
        Resume r = EntityRepository.getInstance().getResumeByApplicantId(vacancy.getApplicantId());
        r.resetInSearch();
        EntityRepository.getInstance().update(r, EntityRepository.RESUME_TYPE);
        vacancy.resetStatus();
    }
}
