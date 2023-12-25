package view;

import presenter.LoginPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener, LoginInterface {
    private JPanel contentPane;
    private JPanel loginPanel;
    private JLabel titleLabel;
    private JLabel wrongAccountLabel;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton showPasswordButton;
    private JButton loginButton;
    private JLabel registerLabel;
    private JButton registerButton;

    private final LoginPresenter loginPresenter = LoginPresenter.initializeLoginPresenter(this);

    public LoginView(String name) {
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareLoginGui();
        this.setContentPane(this.contentPane);
    }

    private void prepareLoginGui() {
        this.loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.3;
        c.weighty = 0.9;

        this.titleLabel = new JLabel("Login to your account");
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.loginPanel.add(this.titleLabel, c);

        c.gridy = 1;
        this.wrongAccountLabel = new JLabel("");
        this.wrongAccountLabel.setFont(new Font("Serif",Font.PLAIN,20));
        this.loginPanel.add(this.wrongAccountLabel,c);

        this.usernameLabel = new JLabel("Username");
        this.usernameLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.gridy = 2;
        c.gridwidth = 1;
        this.loginPanel.add(this.usernameLabel, c);

        this.usernameTextField = new JTextField();
        this.usernameTextField.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridwidth = 3;
        this.loginPanel.add(this.usernameTextField, c);

        this.passwordLabel = new JLabel("Password");
        this.passwordLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.CENTER;
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        this.loginPanel.add(this.passwordLabel, c);

        this.passwordField = new JPasswordField();
        this.passwordField.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.passwordField.setEchoChar('*');
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridwidth = 3;
        this.loginPanel.add(this.passwordField, c);

        this.showPasswordButton = new JButton("Show password");
        this.showPasswordButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.showPasswordButton.addActionListener(this);
        this.showPasswordButton.setActionCommand("SHOW PASSWORD");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        this.loginPanel.add(this.showPasswordButton, c);

        this.loginButton = new JButton("Login");
        this.loginButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.loginButton.addActionListener(this);
        this.loginButton.setActionCommand("LOGIN");
        c.gridx = 1;
        c.gridwidth = 3;
        this.loginPanel.add(this.loginButton, c);

        this.registerLabel = new JLabel("Don't have an account? Register now!");
        this.registerLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
        this.loginPanel.add(this.registerLabel, c);

        this.registerButton = new JButton("Register");
        this.registerButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.registerButton.addActionListener(this);
        this.registerButton.setActionCommand("REGISTER");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 6;
        this.loginPanel.add(this.registerButton, c);

        this.contentPane.add(this.loginPanel);
    }

    @Override
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    @Override
    public void showWrongPassword() {
        this.wrongAccountLabel.setText("Wrong username and password combination");
    }

    @Override
    public void disposeWindow() {
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SHOW PASSWORD" -> {
                loginPresenter.toggleShowPassword();
            }
            case "LOGIN" -> {
                loginPresenter.processLoginAction();
            }
            case "REGISTER" -> {
                loginPresenter.processRegisterAction();
            }
        }
    }
}
