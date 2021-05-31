package personenkartei;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Alert {
    JDialog alert = new JDialog();
    private String title;
    private String text;
    private boolean exitOnClose;

    public Alert(String title, String text, boolean exitOnClose) {

        this.title = title;
        this.text = text;
        this.exitOnClose = exitOnClose;
        configure();
    }

    private void configure() {
        alert.setTitle(title);
        alert.setPreferredSize(new Dimension(250, 100));
        alert.add(new JLabel(text, SwingConstants.CENTER));
        alert.setLocationRelativeTo(null);
        alert.setAlwaysOnTop(true);
        alert.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (exitOnClose) {
                    System.exit(3);
                }
            }
        });
    }

    public void show() {
        alert.pack();
        alert.setVisible(true);
    }
}
