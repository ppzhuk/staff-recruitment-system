package recruitment.facade;

import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;
import recruitment.repository.ApplicantRepository;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;
import recruitment.repository.PersonRepository;

/**
 * Created by Zhuk Pavel on 06.06.2016.
 */
public class Facade {
    
    public Vacancy getVacancy(int vacancyId) {
        return (Vacancy)
                EntityRepository.getInstance().getById(vacancyId, EntityRepository.VACANCY_TYPE);
    }
    
    public Resume getResume(int resumeId) {
        return (Resume) 
                EntityRepository.getInstance().getById(resumeId, EntityRepository.RESUME_TYPE);
    }
    
    public String getApplicantFIO(Resume resume) {
        return ApplicantRepository.getInstance().getById(resume.getApplicantId()).getName();
    }
    
    public int getEmployerIdByPersonId(int personId) {
        return EmployerRepository.getInstance()
                .getByPersonId(personId)
                .getEmployerId();
    }

    public int getApplicantIdByPersonId(int personId) {
        return ApplicantRepository.getInstance()
                .getByPersonId(personId)
                .getApplicantId();
    }
    
    public int getPersonIdByEmployerId(int employerId) {
        return EmployerRepository.getInstance().getById(employerId).getPersonId();
    }

    public int getPersonIdByApplicantId(int applicantId) {
        return ApplicantRepository.getInstance().getById(applicantId).getPersonId();
    }
    
    public Person getPerson(int employerPersonId) {
        return PersonRepository.getInstance().getById(employerPersonId); 
    }
    
    public void deleteVacancy(int vacancyId) {
        EntityRepository.getInstance().remove(vacancyId, EntityRepository.VACANCY_TYPE);
    }

    public void deleteResume(int resumeId) {
        EntityRepository.getInstance().remove(resumeId, EntityRepository.RESUME_TYPE);
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
    
    public void openResume(Resume resume) {
        Vacancy v = (Vacancy)
                EntityRepository.getInstance().getById(resume.getEmployerVacancyId(), EntityRepository.VACANCY_TYPE);
        v.resetStatus();
        EntityRepository.getInstance().update(v, EntityRepository.VACANCY_TYPE);
        resume.resetInSearch();
    }

    public void updateResume(Resume resume, String experience, String skills, String education, String description) {
        resume.setExperience(experience);
        resume.setSkills(skills);
        resume.setEducation(education);
        resume.setDescription(description);
        EntityRepository.getInstance().update(resume, EntityRepository.RESUME_TYPE);
    }
}
