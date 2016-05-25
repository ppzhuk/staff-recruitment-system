package recruitment.models;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Applicant extends Person {
    private int id;

    public Applicant(String name, String email, String login, String password) {
        super(name, email, login, password);
    }

    public Applicant(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());
        this.id = id;
    }
    
    public int getEmployerId() {
        return id;
    }

    public void setEmployerId(int id) {
        this.id = id;
    }
}
