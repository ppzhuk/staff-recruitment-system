package recruitment.mappers;

import com.google.gson.Gson;
import org.sql2o.Connection;
import recruitment.models.Mark;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zhuk Pavel on 12.06.2016.
 */
public class ExternalMarkMapper {
    private String pathToSource;
    private FileReader reader;

    public ExternalMarkMapper(String pathToSource) {
        this.pathToSource = pathToSource;
        try {
            reader = new FileReader(pathToSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public List<Mark> getAll() {
        Mark[] marks = new Gson().fromJson(reader, Mark[].class);
        return Arrays.asList(marks);
    }

    public List<Mark> getPersonMarks(int evaluatedPersonId) {
        Mark[] marks = new Gson().fromJson(reader, Mark[].class);
        return Arrays.asList(marks)
                .stream()
                .filter( m -> m.getEvaluatedPersonId() == evaluatedPersonId)
                .collect(Collectors.toList());
    }
}
