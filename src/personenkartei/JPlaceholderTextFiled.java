package personenkartei;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class JPlaceholderTextFiled extends JTextField {
    private String placeholder;

    public JPlaceholderTextFiled() {
    }

    public JPlaceholderTextFiled(
            final Document pDoc,
            final String pText,
            final int pColumns) {
        super(pDoc, pText, pColumns);
    }

    public JPlaceholderTextFiled(final int pColumns) {
        super(pColumns);
    }

    public JPlaceholderTextFiled(final String pText) {
        super(pText);
    }

    public JPlaceholderTextFiled(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
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

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

    public boolean hasLessCharsThen(int amount) {
        return !this.hasMoreCharsThen(amount);
    }
}
