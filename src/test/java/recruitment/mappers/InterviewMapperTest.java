package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Interview;
import recruitment.models.Vacancy;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Nataly on 03.06.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterviewMapperTest {
    InterviewMapper mapper = new InterviewMapper();

    @Test
    public void _1_testGetAll() {
        assertEquals(3, mapper.getAll().size());
    }

    @Test
    public void _2_testGetById() throws ParseException {
        Interview v = mapper.getById(1);
        assertEquals(1, v.getApplicantId());
        assertEquals(1, v.getVacancyId());
        assertEquals("2016.06.11", v.getInterviewDate());
        assertEquals(0, v.getResultApplicant());
        assertEquals(0, v.getResultEmployer());

        v = mapper.getById(2);
        assertEquals(2, v.getApplicantId());
        assertEquals(2, v.getVacancyId());
        assertEquals("2016.06.07", v.getInterviewDate());
        assertEquals(0, v.getResultApplicant());
        assertEquals(0, v.getResultEmployer());

        v = mapper.getById(3);
        assertEquals(3, v.getApplicantId());
        assertEquals(3, v.getVacancyId());
        assertEquals("2016.05.13", v.getInterviewDate());
        assertEquals(1, v.getResultApplicant());
        assertEquals(1, v.getResultEmployer());
    }

    @Test
    public void _3_testInsert() {
        Interview p = new Interview(1, 2, Vacancy.getToday());
        long id = mapper.save(p);
        Interview v = mapper.getById(id);
        assertEquals(p.getApplicantId(), v.getApplicantId());
        assertEquals(p.getVacancyId(), v.getVacancyId());
        assertEquals(0, v.getResultApplicant());
        assertEquals(0, v.getResultEmployer());
        assertEquals(Vacancy.getToday(), v.getInterviewDate());
    }

    @Test
    public void _4_testUpdate() throws ParseException  {
        Interview p = new Interview(1, 2, Vacancy.getToday());
        p.setResultApplicant(Interview.RESULT_POSITIVE);
        p.setResultEmployer(Interview.RESULT_POSITIVE);
        List<Interview> list =  mapper.getAll();
        int update_id = list.get(list.size()-1).getId();
        p.setId(update_id);
        mapper.update(p);
        Interview v = mapper.getById(update_id);
        assertEquals(p.getInterviewResult(), v.getInterviewResult());
    }

    @Test
    public void _5_testDelete() {
        List<Interview> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
