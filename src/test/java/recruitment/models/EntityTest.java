package recruitment.models;

import org.junit.Test;
import recruitment.repository.EntityRepository;
import recruitment.repository.ManagerRepository;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pavel on 25.05.2016.
 */
public class EntityTest {
    EntityRepository repo = new EntityRepository();
    
    @Test
    public void testGetById() {
        Mark m = (Mark) repo.getById(2, EntityRepository.MARK_TYPE);
        assertEquals("Плохо.", m.getComment());
        assertEquals(3, m.getMark());
        
        Vacancy v = (Vacancy) repo.getById(3, EntityRepository.VACANCY_TYPE);
        assertEquals(Vacancy.STATUS_CLOSE, v.getStatus());
    }
}
