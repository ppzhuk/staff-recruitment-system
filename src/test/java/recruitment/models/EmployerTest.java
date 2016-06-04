package recruitment.models;

import org.junit.Test;
import recruitment.repository.EmployerRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmployerTest {
    EmployerRepository repo = EmployerRepository.getInstance();

    @Test
    public void testGetByLogin() {
        Employer e = repo.getByLoginAndPass("manager1", "manager1");
        assertNull(e);
        e = repo.getByLoginAndPass("emplr1", "emplr1");
        assertEquals("Работодатель1 Работодатель Работодатель", e.getName());
    }

    @Test
    public void testGetById() {
        Person p = repo.getById(2);
        assertEquals("Работодатель2 Работодатель Работодатель", p.getName());
        assertEquals(2, ((Employer) p).getEmployerId());
        assertEquals(5, p.getPersonId());
    }

}
