package recruitment.mappers;

import org.sql2o.Sql2o;

import java.util.Map;

/**
 * Created by Zhuk Pavel on 29.05.2016.
 */
public class BaseMapper {
    protected Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/recruitment", "root", "Pavel");
}
