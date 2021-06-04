package personenkartei.views;

import personenkartei.*;
import personenkartei.interfaces.View;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonGrid extends DefaultView implements View {
    private final CsvDao dao;
    private final Dimension screenDimension;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JMenu removePersonButton;
    private JTable jTable;

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
        jTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        jTable.setColumnSelectionAllowed(false);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                removePersonButton.setVisible(!jTable.getSelectionModel().isSelectionEmpty());
            }
        });

        final Color noEditColor = Color.decode("#E4E4E4");

        DefaultTableCellRenderer idRender = new DefaultTableCellRenderer();
        idRender.setBackground(noEditColor);

        JTableHeader header = jTable.getTableHeader();
        header.setBackground(noEditColor);

        jTable.getColumnModel().getColumn(0).setCellRenderer(idRender);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(1);

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

        var newPersonButton = new JMenu("Hinzufügen");
        newPersonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newPersonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addNewPerson(false);
            }
        });

        var newSamplePersonButton = new JMenu("Musterperson Hinzufügen");
        newSamplePersonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newSamplePersonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addNewPerson(true);
            }
        });

        var saveButton = new JMenu("Save");
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                makeSave();
                new Alert("Saved", "All changes have been saved", false).show();
            }
        });

        removePersonButton = new JMenu("Entfernen");
        removePersonButton.setVisible(false);
        removePersonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removePersonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                removePerson();
            }
        });

        var closeButton = new JMenu("Zurück");
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.hide();
                new StartUp(screenDimension).show();
            }
        });


        JMenuBar bar = new JMenuBar();
        JMenu personJMenu = new JMenu("Person");
        bar.add(personJMenu);
        personJMenu.add(newPersonButton);
        personJMenu.add(removePersonButton);
        personJMenu.add(newSamplePersonButton);
        bar.add(saveButton);
        bar.add(closeButton);
        frame.setJMenuBar(bar);
        bar.setVisible(true);
        bar.repaint();
        frame.add(scrollPane);
    }

    private void removePerson() {
        if (jTable.getSelectedRow() != -1) {
            tableModel.removeRow(jTable.getSelectedRow());
            removePersonButton.setVisible(false);
        }
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
