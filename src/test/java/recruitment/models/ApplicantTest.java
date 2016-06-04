package recruitment.models;

import org.junit.Test;
import recruitment.repository.ApplicantRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Pavel on 25.05.2016.
 */
public class ApplicantTest {
    ApplicantRepository repo = ApplicantRepository.getInstance();

    @Test
    public void testGetByLogin() {
        Applicant e = repo.getByLoginAndPass("manager1", "manager1");
        assertNull(e);
        e = repo.getByLoginAndPass("aplcnt3", "aplcnt3");
        assertEquals("Соискатель3 Соискатель Соискатель", e.getName());
    }

    @Test
    public void testGetById() {
        Person p = repo.getById(3);
        assertEquals("Соискатель3 Соискатель Соискатель", p.getName());
        assertEquals(3, ((Applicant) p).getApplicantId());
        assertEquals(9, p.getPersonId());
    }
}
