package recruitment;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import recruitment.models.Person;
import recruitment.repository.EntityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 25.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/recruitment", "root", "Pavel");

        String sql =
                "SELECT * FROM person";

/*        Map<String, String> colMaps = new HashMap<String,String>();
        colMaps.put();

        sql2o.setDefaultColumnMappings(colMaps);
        */

        try(Connection con = sql2o.open()) {
            List<Person> persons = con.createQuery(sql)
                    .addColumnMapping("phone_number", "phoneNumber")
                    .executeAndFetch(Person.class);
            System.out.println("persons number: " + persons.size());
            Person p = persons.get(0);
            System.out.println(p.getName());
            System.out.println(p.getLogin());
                    
        }
    }
}
