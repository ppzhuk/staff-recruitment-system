package recruitment.repository;

import recruitment.models.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataly on 23.05.2016.
 */
public class ManagerRepository {
    private List<Manager> managers;

    public ManagerRepository() {
        managers = new ArrayList<Manager>();
        managers.add(new Manager("Жук Павел Павлович", "some@email.com"));
        managers.add(new Manager("Иванов Иван Иванович", "another@email.com"));
        managers.add(new Manager("Алексеев Алексей Алексеевич", "email@email.com"));
    }

    public List<Manager> getManagersList() {
        return managers;
    }

    public void getManager(Manager m) {
        managers.add(m);
    }

    public Manager getManager(int i) {
        return managers.get(i);
    }
}
