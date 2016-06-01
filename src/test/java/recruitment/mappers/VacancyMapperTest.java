package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Resume;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nataly on 01.06.2016.
*/

 @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VacancyMapperTest {
    VacancyMapper mapper = new VacancyMapper();

    @Test
    public void _1_testGetAll() {
        assertEquals(3, mapper.getAll().size());
    }

    @Test
    public void _2_testGetById() {
        assertEquals("Должность3", mapper.getById(3).getPosition());
    }

    /*@Test
    public void _3_testInsert() {
        Resume p = new Resume(1, "exp", "skils", "edu");
        long id = mapper.save(p);
        assertEquals(p.getSkills(),mapper.getById(id).getSkills());
    }

    @Test
    public void _4_testUpdate() {
        Resume p = new Resume(1, "exp", "навыки", "edu");
        List<Resume> list =  mapper.getAll();
        int update_id = list.get(list.size()-1).getId();
        p.setId(update_id);
        mapper.update(p);
        assertEquals(p.getSkills(),mapper.getById(update_id).getSkills());
    }

    @Test
    public void _5_testDelete() {
        List<Resume> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }*/
}
