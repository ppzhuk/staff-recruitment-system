package recruitment.mappers;

import org.sql2o.Connection;
import org.sql2o.data.Row;
import recruitment.models.Manager;
import recruitment.models.Person;

import java.util.ArrayList;
import java.util.List;

public class ManagerMapper extends BaseMapper implements DataMapper<Manager> {

    @Override
    public List<Manager> getAll() {
        List<Row> managers;
        List<Row> persons;

        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM manager";
            managers = con.createQuery(sql).executeAndFetchTable().rows();
            sql = "SELECT * FROM person";
            persons = con.createQuery(sql).executeAndFetchTable().rows();
        }
        List<Manager> list = new ArrayList<>();
        for (Row manager : managers) {
            Row person = persons.stream()
                    .filter(p -> p.getInteger("id").equals(manager.getInteger("person_id")))
                    .findFirst().orElse(null);
            list.add(formManager(manager, person));
        }
        return list;
    }

    private Manager formManager(Row m, Row p) {
        Person prsn = new Person(
                p.getInteger("id"),
                p.getString("name"),
                p.getString("email"),
                p.getString("login"),
                p.getString("password")
        );
        prsn.setPhoneNumber(p.getString("phone_number"));

        return new Manager(
                m.getInteger("id"),
                prsn
        );
    }

    @Override
    public Manager getById(long id) {
        List<Row> managers;
        List<Row> persons;

        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM manager WHERE id = :id";
            managers = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable().rows();
            sql = "SELECT * FROM person WHERE id = :person_id";
            persons = con.createQuery(sql)
                    .addParameter("person_id", managers.get(0).getInteger("person_id"))
                    .executeAndFetchTable().rows();
        }
        return formManager(managers.get(0), persons.get(0));
    }

    @Override
    public long save(Manager mngr) {
        PersonMapper pm = new PersonMapper();
        long person_id = pm.save(mngr);
        String sql =
                "INSERT INTO manager (person_id) VALUES " +
                        "(:pid)";
        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("pid", person_id)
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Manager mngr) {
        PersonMapper pm = new PersonMapper();
        pm.update(mngr);
    }

    @Override
    public void delete(Manager mngr) {
        String sql =
                "DELETE FROM manager " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", mngr.getManagerId())
                    .executeUpdate();
        }
        PersonMapper pm = new PersonMapper();
        pm.delete(mngr);
    }
}
