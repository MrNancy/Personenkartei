package personenkartei;

import javax.swing.*;

public class JFormTextField {
    private final PersonField personFieldTitle;
    private final JPanel panel;

    public JFormTextField(PersonField personFieldTitle, JPanel panel) {
        this.personFieldTitle = personFieldTitle;
        this.panel = panel;
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
