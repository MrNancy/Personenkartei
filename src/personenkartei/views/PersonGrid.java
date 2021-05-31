package personenkartei.views;

import personenkartei.*;
import personenkartei.interfaces.View;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonGrid extends DefaultView implements View {
    private final CsvDao dao;
    private JScrollPane scrollPane;
    private JTable jTable;
    private DefaultTableModel tableModel;
    private final Dimension screenDimension;

    public PersonGrid(Dimension screenDimension, JFrame previousFrame, CsvDao dao) {
        super(screenDimension, previousFrame);
        this.dao = dao;
        this.screenDimension = screenDimension;
        loadData();
        putData(dao.getPersonList());
        setContent();
    }

    private void putData(PersonList personList) {
        Object[] columns = {PersonField.ID, PersonField.FIRSTNAME, PersonField.LASTNAME, PersonField.BIRTHDAY, PersonField.STREET, PersonField.HOUSENUMBER, PersonField.ZIP, PersonField.CITY, PersonField.EMAIL};
        tableModel = new DefaultTableModel(columns, 0);
        for (Person person : personList.getList()) {
            tableModel.addRow(new Object[]{person.getId(), person.getFirstName(), person.getLastName(), person.getBirthday(), person.getStreet(), person.getHouseNumber(), person.getZip(), person.getCity(), person.getEmail()});
        }
        jTable = new JTable(tableModel);
        Rectangle cellRect = jTable.getCellRect(0, 0, true);
        jTable.scrollRectToVisible(cellRect);
        scrollPane = new JScrollPane(jTable);
        TableModelEvent tableModelEvent = new TableModelEvent(new DefaultTableModel());
        tableModel.addTableModelListener(e -> {
            Main.debug("Data integrity change has been detected!");
            Main.debug("Updating " + dao.getFilePath());
            makeSave();
        });
        jTable.tableChanged(tableModelEvent);
    }

    @Override
    public void setContent() {
        frame.setTitle("Personenkartei | Liste");

        var newPersonButton = new JMenu("Person hinzufügen");
        newPersonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newPersonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addNewPerson(false);
            }
        });

        var newSamplePersonButton = new JMenu("Sample Person hinzufügen");
        newSamplePersonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newSamplePersonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addNewPerson(true);
            }
        });

        JMenuBar bar = new JMenuBar();
        bar.add(newPersonButton);
        bar.add(newSamplePersonButton);
        frame.setJMenuBar(bar);
        bar.setVisible(true);
        bar.repaint();
        frame.add(scrollPane);
    }

    private void addNewPerson(boolean sampleUser) {
        var newPerson = new NewPerson(screenDimension, dao, tableModel, sampleUser);
        newPerson.show();
    }

    public void loadData() {
        try {
            dao.setPersonList(dao.readAllToList());
        } catch (IOException ioException) {
            new Alert("Laden fehlgeschlagen", "Die Datei hat nicht die notwendige Formatierung", true).show();
        }
    }

    private void makeSave() {
        List<Object[]> stringCollection = new ArrayList<>();
        for (int count = 0; count < tableModel.getRowCount(); count++) {
            var firstname = tableModel.getValueAt(count, 1).toString();
            var lastname = tableModel.getValueAt(count, 2).toString();
            var birthday = tableModel.getValueAt(count, 3).toString();
            var street = tableModel.getValueAt(count, 4).toString();
            var house = tableModel.getValueAt(count, 5).toString();
            var zip = tableModel.getValueAt(count, 6).toString();
            var city = tableModel.getValueAt(count, 7).toString();
            var email = tableModel.getValueAt(count, 8).toString();
            stringCollection.add(new String[]{firstname, lastname, birthday, street, house, zip, city, email});
        }
        if (!dao.save(stringCollection)) {
            new Alert("Speichern fehlgeschlagen", "Die Datei " + dao.getFilePath() + " konnte nicht abgespeichert werden.", false);
        }
    }
}
