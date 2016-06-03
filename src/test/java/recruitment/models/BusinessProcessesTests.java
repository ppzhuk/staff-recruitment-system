package recruitment.models;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.repository.ApplicantRepository;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;
import recruitment.repository.ManagerRepository;
import recruitment.repository.PersonRepository;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessProcessesTests {
    PersonRepository personRepository = PersonRepository.getInstance();
    ApplicantRepository applicantRepository = ApplicantRepository.getInstance();
    EmployerRepository employerRepository = EmployerRepository.getInstance();
    ManagerRepository managerRepository = ManagerRepository.getInstance();
    EntityRepository entityRepository = EntityRepository.getInstance();
    
    @Test
    public void _1_interviewOrganizationTest() {
        int vacancyId = 2;
        int resumeId = 2;
        Manager manager = managerRepository.getById(1);
        
        assertEquals("Менеджер1 Менеджер Менеджер", manager.getName());
        
        Vacancy v = manager.getVacancy(vacancyId);
        double mark = manager.getPersonMark(v.getEmployerId());
        while (vacancyId < entityRepository.getAll(EntityRepository.VACANCY_TYPE).size() &&
               mark < Mark.SATISFACTORY_MARK) {
            v = manager.getVacancy(++vacancyId);
            mark = manager.getPersonMark(v.getEmployerId());
        }
        
        assertEquals(2, vacancyId);
        assertEquals("Работодатель2 Работодатель Работодатель", employerRepository.getById(v.getEmployerId()).getName());
        
        boolean foundVacancy = false;
        boolean foundResume = false;
        
        if (vacancyId < entityRepository.getAll(EntityRepository.VACANCY_TYPE).size()+1) {
            
            foundVacancy = true;
            
            Resume r = manager.getResume(resumeId);
            mark = manager.getPersonMark(r.getApplicantId());
            while (resumeId < entityRepository.getAll(EntityRepository.RESUME_TYPE).size() &&
                    mark < Mark.SATISFACTORY_MARK) {
                r = manager.getResume(++resumeId);
                mark = manager.getPersonMark(r.getApplicantId());
            }

            assertEquals(2, resumeId);
            assertEquals("Соискатель2 Соискатель Соискатель", applicantRepository.getById(r.getApplicantId()).getName());
            
            if (resumeId < entityRepository.getAll(EntityRepository.RESUME_TYPE).size()+1) {
                
                foundResume = true;
                
                manager.createInterview(r.getApplicantId(), v.getId(), Vacancy.getToday());
            }
        }
        
        assertTrue(foundResume);
        assertTrue(foundVacancy);
        assertEquals(4, ((Interview)entityRepository.getById(4, EntityRepository.INTERVIEW_TYPE)).getId());
        assertEquals(2, ((Interview)entityRepository.getById(4, EntityRepository.INTERVIEW_TYPE)).getApplicantId());
        assertEquals(2, ((Interview)entityRepository.getById(4, EntityRepository.INTERVIEW_TYPE)).getVacancyId());
    }


    @Test
    public void _2_interviewResultTest() {
        Interview i = (Interview) entityRepository.getById(4, EntityRepository.INTERVIEW_TYPE);

        assertEquals(4, i.getId());
        assertEquals(2, i.getApplicantId());
        assertEquals(2, i.getVacancyId());
        
        Employer e = i.getEmployer();
        Applicant a = i.getApplicant();
        i.setResultEmployer(Interview.RESULT_POSITIVE);
        i.setResultApplicant(Interview.RESULT_POSITIVE);
        
        assertEquals(Interview.RESULT_POSITIVE, i.getInterviewResult());
        
        if (i.getInterviewResult() == Interview.RESULT_POSITIVE) {
            e.closeVacancy(i.getVacancyId(), i.getApplicantId());
            a.setFoundJob(i.getVacancyId());
        }
        
        assertEquals(Vacancy.STATUS_CLOSE, 
                ((Vacancy)entityRepository.getById(i.getVacancyId(), EntityRepository.VACANCY_TYPE)).getStatus());
        assertTrue(!a.getResume().isInSearch());
    }

    @Test
    public void _3_feedbackTest() {
        Vacancy v = (Vacancy) entityRepository.getById(2, EntityRepository.VACANCY_TYPE);
        
        assertEquals(999999, v.getSalary(), 0.001);
        assertEquals(2, v.getApplicantId());
        
        int vacancyStatus = v.getStatus();
        
        assertEquals(Vacancy.STATUS_CLOSE, vacancyStatus);
               
        Manager m = managerRepository.getById(1);
        m.createMark(5, applicantRepository.getById(v.getApplicantId()).getPersonId());
        
        Mark mark = (Mark) entityRepository.getById(
                entityRepository.getAll(EntityRepository.MARK_TYPE).size(),
                EntityRepository.MARK_TYPE
        );

        assertEquals(5, mark.getMark());
        
        mark.setComment("someComment");

        m.createMark(5, employerRepository.getById(v.getEmployerId()).getPersonId());

        Mark mark2 = (Mark) entityRepository.getById(
                entityRepository.getAll(EntityRepository.MARK_TYPE).size(),
                EntityRepository.MARK_TYPE
        );

        assertEquals(5, mark2.getMark());

        mark2.setComment("someComment");

        m.deleteMark(mark2.getId());
        m.deleteMark(mark.getId());


        mark2 = (Mark) entityRepository.getById(
                entityRepository.getAll(EntityRepository.MARK_TYPE).size(),
                EntityRepository.MARK_TYPE
        );

        assertEquals(4, mark2.getId());
    }
}
