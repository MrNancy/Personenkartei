package personenkartei;

import personenkartei.views.StartUp;

import java.awt.*;

public class Main {

    private static boolean debugMode = false;

    public static Dimension getScreenDimension() {
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public static void debug(Object object) {
        if (Main.debugMode) {
            System.out.println(object);
        }
    }

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("debug")) {
                Main.debugMode = true;
                break;
            }
        }
        if (!Main.debugMode) {
            System.out.println("If you want to put the application in debug mode, add the program argument: debug");
        }
        Main.debug("Debug mode is enabled");

        new StartUp(getScreenDimension()).show();
    }
}
