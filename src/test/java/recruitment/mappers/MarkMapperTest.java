package recruitment.mappers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import recruitment.models.Mark;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nataly on 01.06.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarkMapperTest {
    MarkMapper mapper = new MarkMapper();

    @Test
    public void _1_testGetAll() {
        assertEquals(4, mapper.getAll().size());
    }

    @Test
    public void _2_testGetById() {
        assertEquals(6, mapper.getById(3).getEvaluatedPersonId());
    }

    @Test
    public void _3_testInsert() {
        Mark p = new Mark(1, 4, 5);
        p.setComment("some comment");
        long id = mapper.save(p);
        assertEquals(p.getComment(),mapper.getById(id).getComment());
    }

    @Test
    public void _4_testUpdate() {
        Mark p = new Mark(1, 4, 1);
        List<Mark> list =  mapper.getAll();
        int update_id = list.get(list.size()-1).getId();
        p.setId(update_id);
        mapper.update(p);
        assertEquals(p.getMark(),mapper.getById(update_id).getMark());
    }

    @Test
    public void _5_testDelete() {
        List<Mark> list =  mapper.getAll();
        mapper.delete(list.get(list.size()-1));
        assertEquals(list.size(), mapper.getAll().size()+1);
    }
}
