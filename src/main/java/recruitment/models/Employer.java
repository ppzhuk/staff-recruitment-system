package recruitment.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Employer extends Person {
    private String companyName;
    private String description;
    private String cite;
    private List<Vacancy> vacancies;

    public Employer(String name, String login, String email, String password) {
        super(name, login, email, password);
        vacancies = new ArrayList<Vacancy>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public void addVacancy() {
        vacancies.add(new  Vacancy());
    }

    public void deleteVacancy(Vacancy vacancy) {
        vacancies.remove(vacancy);
    }

}
