package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Employer;
import recruitment.models.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployerMapperTest {
    EmployerMapper mapper = new EmployerMapper();

    @Test
    public void _1_testGetAll() {
        List<Employer> list = mapper.getAll();
        
        assertEquals(3, list.size());
        
        assertEquals("Company1", list.get(0).getCompanyName());
        assertEquals("Company2", list.get(1).getCompanyName());
        assertEquals("Company3", list.get(2).getCompanyName());

        assertEquals("Работодатель1 Работодатель Работодатель", list.get(0).getName());
        assertEquals("Работодатель2 Работодатель Работодатель", list.get(1).getName());
        assertEquals("Работодатель3 Работодатель Работодатель", list.get(2).getName());
    }

    @Test
    public void _2_testGetById() {
        assertEquals("Работодатель2 Работодатель Работодатель", mapper.getById(2).getName());
    }

    @Test
    public void _3_testInsert() {
        Person p = new Person("Person XYZ", "email", "login123", "pass");
        Employer e = new Employer(0, p);
        e.setCompanyName("google");
        long id = mapper.save(e);
        assertEquals(p.getName(),mapper.getById(id).getName());
    }

    @Test
    public void _4_testUpdate() {
        List<Employer> list =  mapper.getAll();
        Employer e = list.get(list.size()-1);
        e.setSite("yandex.com");
        e.setCompanyName("Yandex");
        mapper.update(e);
        assertEquals(e.getName(),mapper.getById(list.get(list.size()-1).getEmployerId()).getName());
        assertEquals(e.getSite(),mapper.getById(list.get(list.size()-1).getEmployerId()).getSite());
    }
    
    @Test
    public void _5_testDelete() {
        List<Employer> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
