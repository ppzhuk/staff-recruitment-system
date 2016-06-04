package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Manager;
import recruitment.models.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zhuk Pavel on 01.06.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManagerMapperTest {
    ManagerMapper mapper = new ManagerMapper();

    @Test
    public void _1_testGetAll() {
        List<Manager> list = mapper.getAll();

        assertEquals(3, list.size());

        assertEquals("Менеджер1 Менеджер Менеджер", list.get(0).getName());
        assertEquals("Менеджер2 Менеджер Менеджер", list.get(1).getName());
        assertEquals("Менеджер3 Менеджер Менеджер", list.get(2).getName());
    }

    @Test
    public void _2_testGetById() {
        assertEquals("Менеджер2 Менеджер Менеджер", mapper.getById(2).getName());
    }

    @Test
    public void _3_testInsert() {
        Person p = new Person("Person SUPERMAN", "email@mail", "login123", "pass");
        Manager e = new Manager(0, p);
        long id = mapper.save(e);
        assertEquals(p.getName(), mapper.getById(id).getName());
    }

    @Test
    public void _4_testUpdate() {
        List<Manager> list = mapper.getAll();
        Manager e = list.get(list.size() - 1);
        e.setName("Anonimus");
        mapper.update(e);
        assertEquals(e.getName(), mapper.getById(list.get(list.size() - 1).getManagerId()).getName());
    }

    @Test
    public void _5_testDelete() {
        List<Manager> list = mapper.getAll();
        mapper.delete(list.get(list.size() - 1));
        assertEquals(list.size(), mapper.getAll().size() + 1);
    }
}
