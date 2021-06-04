package personenkartei;

import org.apache.commons.csv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvDao {
    private final File file;
    CSVFormat format = CSVFormat.EXCEL.withHeader().withDelimiter(';').withQuoteMode(QuoteMode.ALL).withQuote('"');
    private List<Person> personList;

    public CsvDao(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return file.getAbsolutePath();
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

    private void backup(Path backupFile) throws IOException, InterruptedException {
        if (Files.exists(backupFile)) {
            Main.debug("Backup file found! Deleting it.");
            Files.delete(backupFile);
        }
        Files.copy(Path.of(file.getAbsolutePath()), backupFile);

        Process p = Runtime.getRuntime().exec("attrib +H " + backupFile);
        p.waitFor();

        Main.debug("Backup has been created");
    }

    private void purge() throws FileNotFoundException {
        PrintWriter cleaner = new PrintWriter(file);
        cleaner.print("");
        cleaner.close();
        Main.debug("Original file has been purged");
    }

    private void purgeBackup(Path backupFile) throws IOException {
        if (Files.exists(backupFile) && file.length() > 0) {
            Files.delete(backupFile);
            Main.debug("Backup file has been purged");
            return;
        }
        Main.debug("Backup file has not been purged");
    }

    public boolean save(List<Object[]> stringCollection) {
        try {
            Path backupFile = Path.of(file.getAbsolutePath() + ".jbackup");

            backup(backupFile);
            purge();

            BufferedWriter writer = Files.newBufferedWriter(Path.of(file.getAbsolutePath()));
            CSVPrinter csvPrinter = new CSVPrinter(writer, format);

            for (Object[] entry : stringCollection) {
                csvPrinter.printRecord(entry);
            }

            csvPrinter.flush();
            csvPrinter.close();
            writer.close();

            purgeBackup(backupFile);

            return true;
        } catch (IOException | InterruptedException ioException) {
            return false;
        }
    }

    public List<Person> readAllToList() throws IOException {
        List<Person> personList = new ArrayList<>();

        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        CSVParser parser = format.parse(inputStreamReader);
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

            personList.add(person);
        }
        inputStreamReader.close();
        inputStream.close();

        return personList;
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

    public boolean createFile() throws IOException {
        return file.createNewFile();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
