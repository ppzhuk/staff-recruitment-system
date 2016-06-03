package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Mark;
import recruitment.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataly on 30.05.2016.
 */
public class MarkMapper extends BaseMapper implements DataMapper<Mark> {

    MarkMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("manager_id", "managerId");
        colMaps.put("evaluated_person_id", "evaluatedPersonId");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Mark> getAll() {
        String sql = "SELECT * FROM mark";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Mark.class);
        }
    }

    @Override
    public Mark getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM mark " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Mark.class);
        }
    }

    @Override
    public long save(Mark mark) {
        String sql =
                "INSERT INTO mark (manager_id, evaluated_person_id, mark, comment) VALUES" +
                        "(:manager_id, :evaluated_person_id, :mark, :comment)";

        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("manager_id", mark.getManagerId())
                    .addParameter("evaluated_person_id", mark.getEvaluatedPersonId())
                    .addParameter("mark", mark.getMark())
                    .addParameter("comment", mark.getComment())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Mark mark) {
        String sql =
                "UPDATE mark SET manager_id=:manager_id, evaluated_person_id=:evaluated_person_id, mark=:mark, comment=:comment" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("manager_id", mark.getManagerId())
                    .addParameter("evaluated_person_id", mark.getEvaluatedPersonId())
                    .addParameter("mark", mark.getMark())
                    .addParameter("comment", mark.getComment())
                    .addParameter("id", mark.getId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Mark mark) {
        String sql =
                "DELETE FROM mark " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", mark.getId())
                    .executeUpdate();
        }
    }

    public void deletePersonMarks(int id) {
        String sql =
                "DELETE FROM mark " +
                        "WHERE evaluated_person_id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
