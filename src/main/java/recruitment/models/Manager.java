package recruitment.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Manager extends Person {
    private List<Mark> marks;
    private List<Interview> interviews;
    private List<Vacancy> closedVacancies;

    public Manager(String name, String email) {
        super(name, email);
    }

    public void addInterview(Applicant applicant, Vacancy vacancy, Date date) {
        interviews.add(new Interview(applicant, vacancy, date));
    }

    public void addMark(double mark, String comment, Applicant applicant, Vacancy vacancy) {
        marks.add(new Mark());
    }

    public void closeVacancy(Vacancy vacancy) {
        vacancy.setStatus(Vacancy.STATUS_CLOSE);
    }
}
