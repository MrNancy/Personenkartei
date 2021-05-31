package personenkartei;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvDao {
    private final File file;
    private PersonList personList;

    public CsvDao(File file) {
        this.file = file;
    }

    public boolean validateFile() {
        if (!file.exists()) {
            Main.debug("File: " + file.getAbsolutePath() + " does not exist.");
            return false;
        }
        if (!file.canRead()) {
            Main.debug("File: " + file.getAbsolutePath() + " is not readable.");
            return false;
        }
        if (!file.canWrite()) {
            Main.debug("File: " + file.getAbsolutePath() + " is not writeable.");
            return false;
        }
        if (!file.isFile()) {
            Main.debug(file.getAbsolutePath() + " is not a valid file");
            return false;
        }
        return true;
    }

    public boolean save() {
        return false;
    }

    public boolean update(Object object, Object newObject) {
        return false;
    }

    public void delete(Person person) {
    }

    public PersonList readAllToList() throws IOException {
        PersonList personList = new PersonList();

        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        CSVParser parser = CSVFormat.EXCEL.withDelimiter(';').parse(inputStreamReader);
        List<CSVRecord> records = parser.getRecords();

        for (CSVRecord record : records) {
            Person person = new Person(record.getRecordNumber());
            person.setId(record.getRecordNumber())
                    .setFirstName(record.get(0))
                    .setLastName(record.get(1))
                    .setBirthday(record.get(2))
                    .setStreet(record.get(3))
                    .setHouseNumber(record.get(4))
                    .setZip(Integer.parseInt(record.get(5)))
                    .setCity(record.get(6))
                    .setEmail(record.get(7));

            personList.addEntry(person);
        }
        inputStreamReader.close();
        inputStream.close();

        return personList;
    }

    public void addPerson(Person person) {
        personList.addEntry(person);
    }

    public boolean createFile() throws IOException {
        return file.createNewFile();
    }

    public void setPersonList(PersonList personList) {
        this.personList = personList;
    }

    public PersonList getPersonList() {
        return personList;
    }
}
