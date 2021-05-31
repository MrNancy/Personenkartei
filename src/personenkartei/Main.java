package personenkartei;

import personenkartei.views.StartUp;

import java.awt.*;

public class Main  {

    public static Dimension getScreenDimension() {
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public static void debug(Object object) {
        System.out.println(object);
    }

    public static void main(String[] args) {
        new StartUp(getScreenDimension()).show();
    }
}
