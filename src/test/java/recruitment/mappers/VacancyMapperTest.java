package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Nataly on 01.06.2016.
*/

 @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VacancyMapperTest {
    VacancyMapper mapper = new VacancyMapper();

    @Test
    public void _1_testGetAll() {
        assertEquals(3, mapper.getAll().size());
    }

    @Test
    public void _2_testGetById() throws ParseException {
        Vacancy v = mapper.getById(1);
        assertEquals(1, v.getEmployerId());
        assertEquals("Должность1", v.getPosition());
        assertEquals("Какие-то требования", v.getRequirements());
        assertEquals(100000.0, v.getSalary(), 0.0001);
        assertEquals(-1, v.getStatus());
        assertNull(v.getCloseDate());
        assertEquals(-1, v.getApplicantId());

        v = mapper.getById(2);
        assertEquals(2, v.getEmployerId());
        assertEquals("Должность2", v.getPosition());
        assertEquals("Какие-то очень жесткие требования", v.getRequirements());
        assertEquals(999999.0, v.getSalary(), 0.0001);
        assertEquals(-1, v.getStatus());
        assertNull(v.getCloseDate());
        assertEquals(-1, v.getApplicantId());

        v = mapper.getById(3);
        assertEquals(3, v.getEmployerId());
        assertEquals("Должность3", v.getPosition());
        assertEquals(3.99, v.getSalary(), 0.0001);
        assertEquals(1, v.getStatus());
        assertEquals("2016.02.01", v.getCloseDate());
        assertEquals(3, v.getApplicantId());
    }

    @Test
    public void _3_testInsert() {
        Vacancy p = new Vacancy(1, "position", "requirements", 5);
        long id = mapper.save(p);
        Vacancy v = mapper.getById(id);
        assertEquals(p.getApplicantId(), v.getApplicantId());
        assertNull(v.getCloseDate());
        assertNull(p.getCloseDate());
        assertEquals(p.getRequirements(), v.getRequirements());
    }

    @Test
    public void _4_testUpdate() throws ParseException  {
        Vacancy p = new Vacancy(1, "position", "requirements", 5);
        p.setStatus(Vacancy.STATUS_CLOSE, 1);
        List<Vacancy> list =  mapper.getAll();
        int update_id = list.get(list.size()-1).getId();
        p.setId(update_id);
        mapper.update(p);
        Vacancy v = mapper.getById(update_id);
        assertEquals(p.getApplicantId(), v.getApplicantId());
        assertEquals(1, v.getStatus());
        assertEquals("2016.06.01", v.getCloseDate());
    }

    @Test
    public void _5_testDelete() {
        List<Vacancy> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
