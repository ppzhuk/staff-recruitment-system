package recruitment.repository;

import recruitment.models.Mark;

import java.util.ArrayList;
import java.util.List;

public class MarkRepository implements BaseRepository {
    private List<Mark> list;

    public MarkRepository() {
        list = new ArrayList<>(3);
        Mark m = new Mark(1, 5, 5);
        m.setId(1);
        m.setComment("Очень хорошо!");
        list.add(m);
        m = new Mark(2, 7, 3);
        m.setId(2);
        m.setComment("Плохо.");
        list.add(m);
        m = new Mark(3, 6, 1);
        m.setId(3);
        m.setComment("Ужасно.");
        list.add(m);
    }

    @Override
    public void save(Object o) {
        list.add((Mark) o);
    }

    @Override
    public List<Mark> getAll() {
        return list;
    }

    @Override
    public Object getById(int id) {
        return list.get(id - 1);
    }
}
