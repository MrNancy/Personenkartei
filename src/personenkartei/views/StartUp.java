package personenkartei.views;

import personenkartei.Alert;
import personenkartei.CsvDao;
import personenkartei.Main;
import personenkartei.interfaces.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class StartUp extends DefaultView implements View {
    private JFileChooser fileChooser;
    private CsvDao csvDao;

    public StartUp(Dimension screenDimension) {
        super(screenDimension, true);
        setContent();
    }

    @Override
    public void setContent() {
        frame.setTitle("Personenkartei | by Alexander Bier");
        var newButton = new JMenu("Neu");
        var loadButton = new JMenu("Laden");

        var allowedExtension = new FileNameExtensionFilter(".csv Datei", "csv");

        newButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showSaveFileDialog(allowedExtension);
            }
        });
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLoadFileDialog(allowedExtension);
            }
        });
        newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuBar bar = new JMenuBar();
        bar.add(newButton);
        bar.add(loadButton);
        frame.setJMenuBar(bar);
    }

    private void showLoadFileDialog(FileNameExtensionFilter allowedExtension) {
        showDialog("Wähle eine .csv Datei", allowedExtension);
        var result = fileChooser.showOpenDialog(fileChooser);

        var valid = validateFileChooserResponse(result, false);
        if (valid) {
            this.hide();
            new PersonGrid(getScreenDimension(), frame, csvDao).show();
        } else {
            var alert = new Alert("Auswahl fehlgeschlagen", "Datei konnte nicht geöffnet werden", false);
            alert.show();
        }
    }

    private void showSaveFileDialog(FileNameExtensionFilter allowedExtension) {
        showDialog("Datei speichern", allowedExtension);
        var result = fileChooser.showSaveDialog(fileChooser);

        var valid = validateFileChooserResponse(result, true);

        if (valid) {
            try {
                var isCreated = csvDao.createFile();
                if (!isCreated) {
                    saveFailed(allowedExtension);
                }
                this.hide();
                new PersonGrid(getScreenDimension(), frame, csvDao).show();
            } catch (IOException e) {
                saveFailed(allowedExtension);
            }
        } else {
            var alert = new Alert("Speichern fehlgeschlagen", "Keinen zulässigen Speicherort ausgewählt", false);
            alert.show();
        }
    }

    private void saveFailed(FileNameExtensionFilter allowedExtension) {
        var alert = new Alert("Erstellen fehlgeschlagen", "Fehler beim Schreiben der Datei!", false);
        alert.show();
        showSaveFileDialog(allowedExtension);
    }

    private void showDialog(String title, FileNameExtensionFilter allowedExtension) {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(allowedExtension);
        fileChooser.setDialogTitle(title);
    }

    private boolean validateFileChooserResponse(int result, boolean newFile) {
        if (result != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        Main.debug("Validating file: " + filePath);

        File file = new File(filePath);
        csvDao = new CsvDao(file);
        if (newFile) {
            return true;
        }
        return csvDao.validateFile();
    }
}
