package recruitment.models;

import org.junit.Test;
import recruitment.repository.EntityRepository;
import recruitment.repository.PersonRepository;

/**
 * Created by Zhuk Pavel on 04.06.2016.
 */
public class IllegalStateTest {
    PersonRepository personRepository = PersonRepository.getInstance();
    EntityRepository entityRepository = EntityRepository.getInstance();

    @Test(expected = IllegalArgumentException.class)
    public void markTest() {
        Mark mark = (Mark) entityRepository.getById(1, EntityRepository.MARK_TYPE);
        mark.setMark(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void personTestEmail() {
        Person p = personRepository.getById(1);
        p.setEmail("sdfsdf");
    }

    @Test(expected = IllegalArgumentException.class)
    public void personTestlogin() {
        Person p = personRepository.getById(1);
        p.setLogin("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void personTestPass() {
        Person p = personRepository.getById(1);
        p.setPassword("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void vacancyTestSalary() {
        Vacancy v = (Vacancy) entityRepository.getById(1, EntityRepository.VACANCY_TYPE);
        v.setSalary(-12);
    }
}
