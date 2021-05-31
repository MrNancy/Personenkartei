package personenkartei.views;

import personenkartei.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultView {
    private final Dimension screenDimension;
    JFrame frame = new JFrame();

    private final Dimension size;
    private final boolean centered;
    private final String title;
    private final boolean exitOnClose;

    public DefaultView(Dimension screenDimension, boolean exitOnClose) {
        this.screenDimension = screenDimension;
        this.size = new Dimension(screenDimension.width / 2, screenDimension.height / 2);
        this.title = "Personenkartei";
        this.exitOnClose = exitOnClose;
        centered = true;
        configure();
    }

    public DefaultView(Dimension screenDimension, JFrame previousFrame) {
        this.screenDimension = screenDimension;
        this.size = previousFrame.getSize();
        this.title = "Personenkartei";
        centered = false;
        exitOnClose = true;
        frame.setLocation(previousFrame.getLocation());
        configure();
    }

    private void configure() {
        frame.setSize(size);
        frame.setPreferredSize(size);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (centered) {
            frame.setLocationRelativeTo(null);
        }

        if (exitOnClose) {
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Main.debug("Program has been closed by window close event");
                    System.exit(0);
                }
            });
        }
    }

    public void show() {
        frame.pack();
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public Dimension getScreenDimension() {
        return screenDimension;
    }
}
