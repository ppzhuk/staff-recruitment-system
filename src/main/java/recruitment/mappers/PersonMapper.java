package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhuk Pavel on 29.05.2016.
 */
public class PersonMapper extends BaseMapper implements DataMapper<Person> {

    PersonMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("phone_number", "phoneNumber");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Person> getAll() {
        String sql = "SELECT * FROM person";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Person.class);
        }
    }

    @Override
    public Person getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM person " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Person.class);
        }
    }

    @Override
    public long save(Person p) {
        String sql =
                "INSERT INTO person (name, email, phone_number, login, password) VALUES" +
                        "(:name, :email, :phone_number, :login, :password)";

        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("name", p.getName())
                    .addParameter("email", p.getEmail())
                    .addParameter("phone_number", p.getPhoneNumber())
                    .addParameter("login", p.getLogin())
                    .addParameter("password", p.getPassword())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Person p) {
        String sql =
                "UPDATE person SET name=:name, email=:email, phone_number=:phone_number, login=:login, password=:password" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", p.getName())
                    .addParameter("email", p.getEmail())
                    .addParameter("phone_number", p.getPhoneNumber())
                    .addParameter("login", p.getLogin())
                    .addParameter("password", p.getPassword())
                    .addParameter("id", p.getPersonId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Person p) {
        String sql =
                "DELETE FROM person " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", p.getPersonId())
                    .executeUpdate();
        }
    }
}
