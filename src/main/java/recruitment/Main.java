package recruitment;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import recruitment.models.Person;
import recruitment.models.Vacancy;
import recruitment.repository.EntityRepository;

import java.text.FieldPosition;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 25.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(                Vacancy.DATE_FORMAT.format(
                new Date(),
                new StringBuffer(),
                new FieldPosition(0)
        ).toString());
    }
}
