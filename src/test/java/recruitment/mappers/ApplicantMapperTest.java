package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Applicant;
import recruitment.models.Applicant;
import recruitment.models.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicantMapperTest {
    ApplicantMapper mapper = new ApplicantMapper();

    @Test
    public void _1_testGetAll() {
        List<Applicant> list = mapper.getAll();

        assertEquals(3, list.size());

        assertEquals("Соискатель1 Соискатель Соискатель", list.get(0).getName());
        assertEquals("Соискатель2 Соискатель Соискатель", list.get(1).getName());
        assertEquals("Соискатель3 Соискатель Соискатель", list.get(2).getName());
    }

    @Test
    public void _2_testGetById() {
        assertEquals("Соискатель2 Соискатель Соискатель", mapper.getById(2).getName());
    }

    @Test
    public void _3_testInsert() {
        Person p = new Person("Person IRON MAN", "email", "login123", "pass");
        Applicant e = new Applicant(0, p);
        long id = mapper.save(e);
        assertEquals(p.getName(),mapper.getById(id).getName());
    }

    @Test
    public void _4_testUpdate() {
        List<Applicant> list =  mapper.getAll();
        Applicant e = list.get(list.size()-1);
        e.setName("Anonimus");
        mapper.update(e);
        assertEquals(e.getName(),mapper.getById(list.get(list.size()-1).getApplicantId()).getName());
    }

    @Test
    public void _5_testDelete() {
        List<Applicant> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
