package recruitment.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Manager extends Person {
    private int id;

    public Manager(int id, Person p) {
        super(p.getPersonId(), p.getName(), p.getEmail(), p.getLogin(), p.getPassword());  
        this.id = id;
    }

    public Manager(int id, String name, String email, String login, String password) {
        super(name, email, login, password);
        this.id = id;
    }
    
    
    public int getManagerId() {
        return id;
    }

    public void setManagerId(int id) {
        this.id = id;
    }
}
