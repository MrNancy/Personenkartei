package personenkartei;

import java.util.ArrayList;
import java.util.List;

public class PersonList {
    List<Person> list = new ArrayList<>();

    public void addEntry(Person person) {
        list.add(person);
    }

    public List<Person> getList() {
        return list;
    }

}
