package personenkartei;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import java.awt.*;

public class JPlaceholderTextFiled extends JTextField {
    private String placeholder;

    public JPlaceholderTextFiled() {
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    public boolean isValidEmail() {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(this.getText());
    }

    public boolean isValidNumber() {
        try {
            Integer.parseInt(this.getText());
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public boolean isEmpty() {
        String validator = this.getText().trim();
        return validator.equals("");
    }

    public boolean hasMoreCharsThen(int amount) {
        return this.getText().length() > amount;
    }

    public boolean isValidBirthday() {
        DateValidator validator = new DateValidator();
        return validator.isValid(this.getText(), "dd.mm.YYYY");
    }
}
