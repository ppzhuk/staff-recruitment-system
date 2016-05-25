package recruitment.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Employer extends Person {
    private int id;
    private String companyName;
    private String description;
    private String cite;

    public Employer(String name, String email, String login, String password, String companyName) {
        super(name, email, login, password);
        this.companyName = companyName;
    }

    public Employer(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());
        this.id = id;
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

    public int getEmployerId() {
        return id;
    }

    public void setEmployerId(int id) {
        this.id = id;
    }
}
