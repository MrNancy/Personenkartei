package personenkartei;

import java.util.ArrayList;
import java.util.List;

public class PersonList {
    List<Person> list = new ArrayList<>();

    public void addEntry(Person person) {
        list.add(person);
    }

    public void removeEntry(int index) {
        list.remove(index);
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}
