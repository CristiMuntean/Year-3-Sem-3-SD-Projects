package view;

import controller.Language;
import controller.LoginController;
import model.language.Languages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class LoginView extends JFrame implements LoginInterface, Observer {
    private JPanel contentPane;
    private JPanel loginPanel;
    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
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
        Language language = Language.getLanguageInstance();
        this.loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.weighty = 0.9;

        this.changeLanguageEnglishButton = new JButton(language.getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!language.getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.loginPanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(language.getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));

        if(!language.getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.loginPanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(language.getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));

        if(!language.getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.loginPanel.add(this.changeLanguageItalianButton,c);
        }

        c.fill = GridBagConstraints.CENTER;
        this.titleLabel = new JLabel(language.getFields().getLoginTitleLabel());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        this.loginPanel.add(this.titleLabel, c);

        c.gridy = 2;
        this.wrongAccountLabel = new JLabel("");
        this.wrongAccountLabel.setFont(new Font("Serif",Font.PLAIN,20));
        this.loginPanel.add(this.wrongAccountLabel,c);

        this.usernameLabel = new JLabel(language.getFields().getLoginUsernameLabel());
        this.usernameLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.gridy = 3;
        c.gridwidth = 2;
        this.loginPanel.add(this.usernameLabel, c);

        this.usernameTextField = new JTextField();
        this.usernameTextField.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridwidth = 4;
        this.loginPanel.add(this.usernameTextField, c);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                usernameTextField.requestFocus();
            }
        });

        this.passwordLabel = new JLabel(language.getFields().getLoginPasswordLabel());
        this.passwordLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.CENTER;
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        this.loginPanel.add(this.passwordLabel, c);

        this.passwordField = new JPasswordField();
        this.passwordField.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.passwordField.setEchoChar('*');
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridwidth = 4;
        this.loginPanel.add(this.passwordField, c);

        this.showPasswordButton = new JButton(language.getFields().getLoginShowPassword());
        this.showPasswordButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        this.loginPanel.add(this.showPasswordButton, c);

        this.loginButton = new JButton(language.getFields().getLoginLoginButton());
        this.loginButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.gridx = 2;
        c.gridwidth = 4;
        this.loginPanel.add(this.loginButton, c);

        this.registerLabel = new JLabel(language.getFields().getLoginRegisterLabel());
        this.registerLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 6;
        this.loginPanel.add(this.registerLabel, c);

        this.registerButton = new JButton(language.getFields().getLoginRegisterButton());
        this.registerButton.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
//        this.registerButton.addActionListener(loginController);
//        this.registerButton.setActionCommand("REGISTER");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 7;
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
        Language language = Language.getLanguageInstance();
        this.wrongAccountLabel.setText(language.getFields().getLoginWrongAccountLabel());
    }

    @Override
    public void disposeWindow() {
        this.dispose();
    }

    private void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    public void refreshPanel() {
        this.clearPanel();
        this.prepareGui();
    }

    public JButton getChangeLanguageEnglishButton() {
        return changeLanguageEnglishButton;
    }

    public JButton getChangeLanguageRomanianButton() {
        return changeLanguageRomanianButton;
    }

    public JButton getChangeLanguageItalianButton() {
        return changeLanguageItalianButton;
    }

    public JButton getShowPasswordButton() {
        return showPasswordButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel();
    }
}
