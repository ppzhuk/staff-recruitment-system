package recruitment.models;

import org.junit.Test;
import recruitment.repository.ManagerRepository;
import recruitment.repository.PersonRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pavel on 23.05.2016.
 */
public class ManagerTest {
    ManagerRepository repo = ManagerRepository.getInstance();

    @Test
    public void testGetByLogin() {
        Manager m = repo.getByLoginAndPass("manager1", "manager1");
        assertEquals("Менеджер1 Менеджер Менеджер", m.getName());
    }

    @Test
    public void testGetById() {
        Person p = repo.getById(2);
        assertEquals("Менеджер2 Менеджер Менеджер", p.getName());
        assertEquals(2, ((Manager)p).getManagerId());
    }
}
