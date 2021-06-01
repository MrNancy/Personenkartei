package personenkartei;

import javax.swing.*;

public record JFormTextField(PersonField personFieldTitle, JPanel panel) {
    public JPlaceholderTextFiled build() {
        Main.debug("Textfield added: " + personFieldTitle.toString());

        JPlaceholderTextFiled jTextField = new JPlaceholderTextFiled();
        jTextField.setPlaceholder(personFieldTitle.toString());

        panel.add(new JLabel(personFieldTitle.toString()));
        panel.add(jTextField);

        return jTextField;
    }
}
