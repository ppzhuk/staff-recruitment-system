package recruitment.models;

import recruitment.repository.EntityRepository;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Person {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;


    public Person(String name, String email, String login, String password) {
        this.name = name;
        if (!email.contains("@")) {
            throw new IllegalArgumentException("email");
        }
        this.email = email;
        if (login.length() < 4 || password.length() < 4) {
            throw new IllegalArgumentException(" login or pass too short");
        }
        this.login = login;
        this.password = password;
    }

    public Person(int id, String name, String email, String login, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public int getPersonId() {
        return id;
    }

    public void setPersonId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.length() < 4) {
            throw new IllegalArgumentException("login too short");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 4) {
            throw new IllegalArgumentException("pass too short");
        }
        this.password = password;
    }

    public double getAverageMark() {
        EntityRepository repo = EntityRepository.getInstance();
        double mark[] = new double[1];
        mark[0] = 0;
        int count[] = new int[1];
        count[0] = 0;
        repo.getAll(EntityRepository.MARK_TYPE)
                .stream()
                .filter(m -> ((Mark) m).getEvaluatedPersonId() == id)
                .forEach(m -> {
                    mark[0] += ((Mark) m).getMark();
                    count[0] += 1;
                });
        if (count[0] == 0) {            
            return 0;
        }
        return mark[0] / count[0];
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

}
