package view;

import model.User;
import controller.UserPopUpController;

import javax.swing.*;
import java.awt.*;

public class UserPopUpView extends JFrame {
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JLabel emailLabel;
    private JLabel phoneNumberLabel;
    private JTextField idTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField roleTextField;
    private JTextField emailTextField;
    private JTextField phoneNumberTextField;
    private JButton submitButton;
    private final User selectedUser;
    UserPopUpController userPopUpController;

    public UserPopUpView(String name, User selectedUser, OperationsView<User> userOperationsView, UserInterface employeeInterface) {
        super(name);
        this.selectedUser = selectedUser;
        this.userPopUpController = UserPopUpController.getControllerInterface(userOperationsView, employeeInterface, this);
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.preparePopUpView(name);
        this.setContentPane(this.contentPane);
    }

    private void preparePopUpView(String name) {
        this.popUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        this.popUpPanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;

        c.gridy = 1;
        c.gridx = 0;
        this.idLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getId());
        this.idLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.idLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.idTextField = new JTextField();
        this.idTextField.setColumns(10);
        this.popUpPanel.add(this.idTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        this.usernameLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getUsername());
        this.usernameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.usernameLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.usernameTextField = new JTextField();
        this.usernameTextField.setColumns(10);
        this.popUpPanel.add(this.usernameTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        this.passwordLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getPassword());
        this.passwordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.passwordLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.passwordTextField = new JTextField();
        this.passwordTextField.setColumns(10);
        this.popUpPanel.add(this.passwordTextField, c);

        c.gridy = 4;
        c.gridx = 0;
        this.roleLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getRole());
        this.roleLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.roleLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.roleTextField = new JTextField();
        this.roleTextField.setColumns(10);
        this.popUpPanel.add(this.roleTextField, c);

        c.gridy = 5;
        c.gridx = 0;
        this.emailLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getEmail());
        this.emailLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.emailLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.emailTextField = new JTextField();
        this.emailTextField.setColumns(10);
        this.popUpPanel.add(this.emailTextField, c);

        c.gridy = 6;
        c.gridx = 0;
        this.phoneNumberLabel = new JLabel(userPopUpController.getLanguage().getFields().getUserFields().getPhoneNumber());
        this.phoneNumberLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.phoneNumberLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.phoneNumberTextField = new JTextField();
        this.phoneNumberTextField.setColumns(10);
        this.popUpPanel.add(this.phoneNumberTextField, c);

        c.gridy = 7;
        this.submitButton = new JButton(userPopUpController.getLanguage().getFields().getSubmitButton());
        this.submitButton.addActionListener(userPopUpController);
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public JTextField getRoleTextField() {
        return roleTextField;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }
}
