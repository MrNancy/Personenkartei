package personenkartei;

import javax.swing.*;
import java.util.Objects;

public final class JFormTextField {
    private final PersonField personFieldTitle;
    private final JPanel panel;

    public JFormTextField(PersonField personFieldTitle, JPanel panel) {
        this.personFieldTitle = personFieldTitle;
        this.panel = panel;
    }

    public PersonField personFieldTitle() {
        return personFieldTitle;
    }

    public JPanel panel() {
        return panel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JFormTextField) obj;
        return Objects.equals(this.personFieldTitle, that.personFieldTitle) &&
                Objects.equals(this.panel, that.panel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personFieldTitle, panel);
    }

    @Override
    public String toString() {
        return "JFormTextField[" +
                "personFieldTitle=" + personFieldTitle + ", " +
                "panel=" + panel + ']';
    }

    public JPlaceholderTextFiled build() {
        Main.debug("Textfield added: " + personFieldTitle.toString());

        JPlaceholderTextFiled jTextField = new JPlaceholderTextFiled();
        jTextField.setPlaceholder(personFieldTitle.toString());

        panel.add(new JLabel(personFieldTitle.toString()));
        panel.add(jTextField);

        return jTextField;
    }
}
