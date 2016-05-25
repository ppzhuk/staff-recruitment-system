package recruitment.models;

import org.junit.Test;
import recruitment.repository.PersonRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonTest {
    PersonRepository repo = new PersonRepository();
    
    @Test
    public void testGetByLogin() {
        Person p = repo.getByLoginAndPass("manager3", "manager3");
        assertEquals("Менеджер3 Менеджер Менеджер", p.getName());
    }

    @Test
    public void testGetById() {
        Person p = repo.getById(7);
        assertEquals("Соискатель1 Соискатель Соискатель", p.getName());
    }
}
