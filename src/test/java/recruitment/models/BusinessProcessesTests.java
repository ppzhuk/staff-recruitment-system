package recruitment.models;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.repository.ApplicantRepository;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;
import recruitment.repository.ManagerRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessProcessesTests {
    ApplicantRepository applicantRepository = ApplicantRepository.getInstance();
    EmployerRepository employerRepository = EmployerRepository.getInstance();
    ManagerRepository managerRepository = ManagerRepository.getInstance();
    EntityRepository entityRepository = EntityRepository.getInstance();
    

    int interview_id = -1;
    int vacancyId = -1;
    
    @Test
    public void _1_interviewOrganizationTest() {
        Vacancy vacancy = new Vacancy(1, "position", "requirements", 5);
        vacancyId = (int) entityRepository.save(vacancy);
        Resume resume = new Resume(1, "exp", "навыки", "edu");
        int resumeId = (int) entityRepository.save(resume);
        Manager manager = managerRepository.getById(1);
        
        assertEquals("Менеджер1 Менеджер Менеджер", manager.getName());
        
        vacancy = manager.getVacancy(vacancyId);
        
        assertNotNull(vacancy);
        assertTrue(vacancy.getId() > 0);
        
        double mark = manager.getPersonMark(vacancy.getEmployerId());
        while (vacancy.getId() < 1 || mark < Mark.SATISFACTORY_MARK) {
            vacancy = manager.getVacancy(++vacancyId);
            mark = manager.getPersonMark(vacancy.getEmployerId());
        }
        
        assertEquals(vacancy.getId(), vacancyId);
        assertEquals("Работодатель1 Работодатель Работодатель", employerRepository.getById(vacancy.getEmployerId()).getName());
        
        boolean foundVacancy = false;
        boolean foundResume = false;
        
        if (vacancy.getId() > 0) {
            
            foundVacancy = true;
            
            Resume r = manager.getResume(resumeId);

            assertNotNull(r);
            assertTrue(r.getId() > 0);
            
            mark = manager.getPersonMark(r.getApplicantId());
            while (r.getId() < 1 || mark < Mark.SATISFACTORY_MARK) {
                r = manager.getResume(++resumeId);
                mark = manager.getPersonMark(r.getApplicantId());
            }

            assertEquals(r.getId(), resumeId);
            assertEquals("Соискатель1 Соискатель Соискатель", applicantRepository.getById(r.getApplicantId()).getName());
            
            if (r.getId() > 0) {
                
                foundResume = true;
                
                interview_id = (int) manager.createInterview(r.getApplicantId(), vacancy.getId(), Vacancy.getToday());
            }
        }
        
        assertTrue(foundResume);
        assertTrue(foundVacancy);
        assertEquals(interview_id, ((Interview)entityRepository.getById(interview_id, EntityRepository.INTERVIEW_TYPE)).getId());
        assertEquals(1, ((Interview)entityRepository.getById(interview_id, EntityRepository.INTERVIEW_TYPE)).getApplicantId());
        assertEquals(vacancyId, ((Interview)entityRepository.getById(interview_id, EntityRepository.INTERVIEW_TYPE)).getVacancyId());
    }


    @Test
    public void _2_interviewResultTest() {
        List<?> list =   entityRepository.getAll(EntityRepository.INTERVIEW_TYPE);
        Interview i = (Interview) list.get(list.size()-1);
        interview_id = i.getId();
        
        list = entityRepository.getAll(EntityRepository.VACANCY_TYPE);
        Vacancy v = (Vacancy) list.get(list.size()-1);
        vacancyId = v.getId();
        
        assertEquals(interview_id, i.getId());
        assertEquals(1, i.getApplicantId());
        assertEquals(vacancyId, i.getVacancyId());
        
        Employer e = i.getEmployer();
        Applicant a = i.getApplicant();
        i.setResultEmployer(Interview.RESULT_POSITIVE);
        i.setResultApplicant(Interview.RESULT_POSITIVE);

        entityRepository.update(i, EntityRepository.INTERVIEW_TYPE);
        
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
        List<?> list = entityRepository.getAll(EntityRepository.VACANCY_TYPE);
        Vacancy v = (Vacancy) list.get(list.size()-1);
        
        assertEquals(5, v.getSalary(), 0.001);
        assertEquals(1, v.getApplicantId());
        
        int vacancyStatus = v.getStatus();
        
        assertEquals(Vacancy.STATUS_CLOSE, vacancyStatus);
               
        Manager m = managerRepository.getById(1);
        m.createMark(5, applicantRepository.getById(v.getApplicantId()).getPersonId());

        list = entityRepository.getAll(EntityRepository.MARK_TYPE);
        Mark mark = (Mark) list.get(list.size()-1);

        assertEquals(5, mark.getMark());
        
        mark.setComment("someComment");

        m.createMark(4, employerRepository.getById(v.getEmployerId()).getPersonId());

        list = entityRepository.getAll(EntityRepository.MARK_TYPE);
        Mark mark2 = (Mark) list.get(list.size()-1);

        assertEquals(4, mark2.getMark());

        mark2.setComment("someComment2");

        entityRepository.update(mark, EntityRepository.MARK_TYPE);
        entityRepository.update(mark2, EntityRepository.MARK_TYPE);

        assertEquals(mark.getComment(), ((Mark)entityRepository.getById(
                mark.getId(),
                EntityRepository.MARK_TYPE
        )).getComment());
        assertEquals(mark2.getComment(), ((Mark)entityRepository.getById(
                mark2.getId(),
                EntityRepository.MARK_TYPE
        )).getComment());
        
        m.deleteMark(mark2.getId());
        m.deleteMark(mark.getId());
        
        entityRepository.remove(v.getId(), EntityRepository.VACANCY_TYPE);
        list = entityRepository.getAll(EntityRepository.RESUME_TYPE);
        Resume resume = (Resume) list.get(list.size()-1);
        entityRepository.remove(resume.getId(), EntityRepository.RESUME_TYPE);
        list = entityRepository.getAll(EntityRepository.INTERVIEW_TYPE);
        Interview interview = (Interview) list.get(list.size()-1);
        entityRepository.remove(interview.getId(), EntityRepository.INTERVIEW_TYPE);
        
    }
}
