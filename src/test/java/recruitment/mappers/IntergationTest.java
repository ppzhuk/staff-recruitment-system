package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Applicant;
import recruitment.models.Interview;
import recruitment.models.Mark;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zhuk Pavel on 04.06.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntergationTest {
    PersonMapper personMapper = new PersonMapper();
    MarkMapper markMapper = new MarkMapper();
    ApplicantMapper applicantMapper = new ApplicantMapper();
    ResumeMapper resumeMapper = new ResumeMapper();
    VacancyMapper vacancyMapper = new VacancyMapper();
    InterviewMapper interviewMapper = new InterviewMapper();

    @Test
    public void _1_markTest() {
        Person p = new Person("namr", "eml", "lgnspod", "pass");
        int  person_id = (int) personMapper.save(p);
        Mark m = new Mark(1, person_id, 5, "comment");
        int mark_id = (int) markMapper.save(m);
        
        assertEquals("comment", markMapper.getById(mark_id).getComment());
        assertEquals(5, markMapper.getAll().size());

        p.setPersonId(person_id);
        personMapper.delete(p);

        assertEquals(4, markMapper.getAll().size());
    }

    @Test
    public void _2_interviewTest() {
        Person p = new Person("namr", "eml", "lgnspod", "pass");
        Applicant a = new Applicant(0, p);
        int appl_id = (int) applicantMapper.save(a);
        a = applicantMapper.getById(appl_id);

        Resume r = new Resume(appl_id, "exp", "навыки", "edu");
        int res_id = (int) resumeMapper.save(r);
        r.setId(res_id);

        Vacancy v = new Vacancy(1, "position", "requirements", 5);
        int vac_id = (int) vacancyMapper.save(v);
        v.setId(vac_id);

        Interview i = new Interview(appl_id, vac_id, Vacancy.getToday());
        int int_id = (int) interviewMapper.save(i);
        i.setId(int_id);
        
        i.setResultApplicant(Interview.RESULT_POSITIVE);
        i.setResultEmployer(Interview.RESULT_POSITIVE);
        v.setStatus(Vacancy.STATUS_CLOSE, appl_id);
        r.setInSearch(false, vac_id);

        interviewMapper.update(i);
        vacancyMapper.update(v);
        resumeMapper.update(r);
        
        assertEquals(appl_id, vacancyMapper.getById(int_id).getApplicantId());

        applicantMapper.delete(a);

        assertEquals(3, applicantMapper.getAll().size());
        assertEquals(3, resumeMapper.getAll().size());
        assertEquals(9, personMapper.getAll().size());
        assertEquals(-1, interviewMapper.getById(int_id).getApplicantId());
        assertEquals(vac_id, interviewMapper.getById(int_id).getVacancyId());
        assertEquals(-1, vacancyMapper.getById(int_id).getApplicantId());
        
        vacancyMapper.delete(v);
        
        assertEquals(3, interviewMapper.getAll().size());
        assertEquals(3, vacancyMapper.getAll().size());
    }
}
