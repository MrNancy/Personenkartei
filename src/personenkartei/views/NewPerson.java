package personenkartei.views;

import personenkartei.*;
import personenkartei.interfaces.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewPerson extends DefaultView implements View {
    private final CsvDao dao;
    private JPlaceholderTextFiled firstnameTextField;
    private JPlaceholderTextFiled lastnameTextField;
    private JPlaceholderTextFiled birthdayTextField;
    private JPlaceholderTextFiled streetTextField;
    private JPlaceholderTextFiled houseNumberTextField;
    private JPlaceholderTextFiled zipTextField;
    private JPlaceholderTextFiled cityTextField;
    private JPlaceholderTextFiled emailTextField;
    private final DefaultTableModel tableModel;
    private final boolean sampleUser;

    public NewPerson(Dimension screenDimension, CsvDao dao, DefaultTableModel tableModel, boolean sampleUser) {
        super(screenDimension, false);
        this.dao = dao;
        this.tableModel = tableModel;
        this.sampleUser = sampleUser;
        Dimension newSize = new Dimension(screenDimension.width / 5, screenDimension.height / 2);
        frame.setPreferredSize(newSize);
        frame.setSize(newSize);
        frame.setLocationRelativeTo(null);
        frame.setTitle(frame.getTitle() + " | Neue Person");
        frame.setAlwaysOnTop(true);

        setContent();
    }

    @Override
    public void setContent() {
        JPanel jPanel = new JPanel();
        frame.add(jPanel);
        jPanel.setLayout(new GridLayout(18, 1));

        firstnameTextField = new JFormTextField(PersonField.FIRSTNAME, jPanel).build();
        firstnameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                firstnameTextField.setForeground(Color.red);
                if (firstnameTextField.hasMoreCharsThen(1)) {
                    firstnameTextField.setForeground(Color.green);
                }
            }
        });

        lastnameTextField = new JFormTextField(PersonField.LASTNAME, jPanel).build();
        lastnameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                lastnameTextField.setForeground(Color.red);
                if (lastnameTextField.hasMoreCharsThen(1)) {
                    lastnameTextField.setForeground(Color.green);
                }
            }
        });

        birthdayTextField = new JFormTextField(PersonField.BIRTHDAY, jPanel).build();
        birthdayTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                birthdayTextField.setForeground(Color.red);
                if (birthdayTextField.isValidBirthday()) {
                    birthdayTextField.setForeground(Color.green);
                }
            }
        });

        streetTextField = new JFormTextField(PersonField.STREET, jPanel).build();
        streetTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                streetTextField.setForeground(Color.red);
                if (streetTextField.hasMoreCharsThen(1)) {
                    streetTextField.setForeground(Color.green);
                }
            }
        });

        houseNumberTextField = new JFormTextField(PersonField.HOUSENUMBER, jPanel).build();
        houseNumberTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                houseNumberTextField.setForeground(Color.red);
                if (houseNumberTextField.hasMoreCharsThen(1)) {
                    houseNumberTextField.setForeground(Color.green);
                }
            }
        });

        zipTextField = new JFormTextField(PersonField.ZIP, jPanel).build();
        zipTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                zipTextField.setForeground(Color.red);
                if (zipTextField.isValidNumber()) {
                    zipTextField.setForeground(Color.green);
                }
            }
        });

        cityTextField = new JFormTextField(PersonField.CITY, jPanel).build();
        cityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                cityTextField.setForeground(Color.red);
                if (cityTextField.hasMoreCharsThen(1)) {
                    cityTextField.setForeground(Color.green);
                }
            }
        });

        emailTextField = new JFormTextField(PersonField.EMAIL, jPanel).build();
        emailTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                emailTextField.setForeground(Color.red);
                if (emailTextField.isValidEmail()) {
                    emailTextField.setForeground(Color.green);
                }
            }
        });

        JButton button = new JButton("Person anlegen");
        button.setMargin(new Insets(10, 0, 0, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submit(e);
            }
        });
        jPanel.add(button);

        if (sampleUser) {
            firstnameTextField.setText("Max");
            lastnameTextField.setText("Mustermann");
            birthdayTextField.setText("01.01.1900");
            streetTextField.setText("Musterstraße");
            houseNumberTextField.setText("17a");
            zipTextField.setText("12345");
            cityTextField.setText("Musterstadt");
            emailTextField.setText("m.mustermann@musterfirma.de");
        }
    }

    public void submit(MouseEvent e) {
        boolean isValid = !firstnameTextField.isEmpty() && cityTextField.hasMoreCharsThen(1);
        if (lastnameTextField.isEmpty() || !cityTextField.hasMoreCharsThen(1)) {
            isValid = false;
        }
        if (birthdayTextField.isEmpty() || !birthdayTextField.isValidBirthday()) {
            isValid = false;
        }
        if (streetTextField.isEmpty() || !cityTextField.hasMoreCharsThen(1)) {
            isValid = false;
        }
        if (houseNumberTextField.isEmpty() || !cityTextField.hasMoreCharsThen(1)) {
            isValid = false;
        }
        if (zipTextField.isEmpty() || !zipTextField.isValidNumber()) {
            isValid = false;
        }
        if (cityTextField.isEmpty() || !cityTextField.hasMoreCharsThen(1)) {
            isValid = false;
        }
        if (emailTextField.isEmpty() || !emailTextField.isValidEmail()) {
            isValid = false;
        }

        if (isValid) {
            Person person = new Person()
                    .setId(tableModel.getRowCount() + 1)
                    .setFirstName(firstnameTextField.getText())
                    .setLastName(lastnameTextField.getText())
                    .setBirthday(birthdayTextField.getText())
                    .setStreet(streetTextField.getText())
                    .setHouseNumber(houseNumberTextField.getText())
                    .setZip(Integer.parseInt(zipTextField.getText()))
                    .setCity(cityTextField.getText())
                    .setEmail(emailTextField.getText());
            dao.addPerson(person);
            tableModel.addRow(new Object[]{person.getId(), person.getFirstName(), person.getLastName(), person.getBirthday(), person.getStreet(), person.getHouseNumber(), person.getZip(), person.getCity(), person.getEmail()});
            frame.hide();
            frame.dispose();
        } else {
            frame.hide();
            frame.dispose();
            new Alert("Eintragung fehlgeschlagen", "Die Person konnte nicht erstellt werden", false).show();
        }
    }
}
