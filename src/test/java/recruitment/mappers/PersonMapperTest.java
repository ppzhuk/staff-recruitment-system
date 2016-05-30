package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Applicant;
import recruitment.models.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonMapperTest {
    PersonMapper mapper = new PersonMapper();
    
    @Test
    public void _1_testGetAll() {
        assertEquals(9, mapper.getAll().size());
    }

    @Test
    public void _2_testGetById() {
        assertEquals("Соискатель1 Соискатель Соискатель", mapper.getById(7).getName());
    }

    @Test
    public void _3_testInsert() {
        Person p = new Person("Person X", "email", "login", "pass");
        long id = mapper.save(p);
        assertEquals(p.getName(),mapper.getById(id).getName());
    }

    @Test
    public void _4_testUpdate() {
        Person p = new Person("Person Y", "email", "login", "pass");
        List<Person> list =  mapper.getAll();
        int update_id = list.get(list.size()-1).getPersonId();
        p.setPersonId(update_id);
        mapper.update(p);
        assertEquals(p.getName(),mapper.getById(update_id).getName());
    }

    @Test
    public void _5_testDelete() {
        List<Person> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
