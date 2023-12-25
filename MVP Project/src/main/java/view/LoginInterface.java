package view;

import javax.swing.*;

public interface LoginInterface {
    JPasswordField getPasswordField();

    JTextField getUsernameTextField();
    void showWrongPassword();
    void disposeWindow();
}
