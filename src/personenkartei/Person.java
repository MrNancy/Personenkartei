package personenkartei;

public class Person {
    private String firstName;
    private String lastName;
    private String birthday;
    private String street;
    private String houseNumber;
    private int zip;
    private String city;
    private String email;
    private long id;

    public Person(long recordNumber) {
        this.id = Integer.parseInt(String.valueOf(recordNumber));
    }

    public Person() {

    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        Person clone = this;
        clone.firstName = firstName;
        return clone;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        Person clone = this;
        clone.lastName = lastName;
        return clone;
    }

    public String getBirthday() {
        return birthday;
    }

    public Person setBirthday(String birthday) {
        Person clone = this;
        clone.birthday = birthday;
        return clone;
    }

    public String getStreet() {
        return street;
    }

    public Person setStreet(String street) {
        Person clone = this;
        clone.street = street;
        return clone;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public Person setHouseNumber(String houseNumber) {
        Person clone = this;
        clone.houseNumber = houseNumber;
        return clone;
    }

    public int getZip() {
        return zip;
    }

    public Person setZip(int zip) {
        Person clone = this;
        clone.zip = zip;
        return clone;
    }

    public String getCity() {
        return city;
    }

    public Person setCity(String city) {
        Person clone = this;
        clone.city = city;
        return clone;
    }

    public String getEmail() {
        return email;
    }

    public Person setEmail(String email) {
        Person clone = this;
        clone.email = email;
        return clone;
    }

    public long getId() {
        return id;
    }

    public Person setId(long id) {
        Person clone = this;
        clone.id = id;
        return clone;
    }
}
