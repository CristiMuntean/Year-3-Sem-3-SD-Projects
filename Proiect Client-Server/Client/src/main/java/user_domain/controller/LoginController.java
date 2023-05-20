package user_domain.controller;


import client.ProxyClient;
import user_domain.model.User;
import user_domain.view.LoginView;
import user_domain.view.language.Language;
import user_domain.view.language.Languages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginController implements ActionListener {
    private boolean showPassword;
    private final LoginView loginView;
    private final Language language;
    private final ProxyClient proxyClient;

    public LoginController(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
        showPassword = false;
        language = Language.getLanguageInstance();
        loginView = new LoginView(language.getFields().getLoginTitleLabel());
        language.addObserver(loginView);
        addActionListeners();
        loginView.setVisible(true);
        loginView.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void toggleShowPassword() {
        showPassword = !showPassword;
        if (showPassword) loginView.getPasswordField().setEchoChar((char) 0);
        else loginView.getPasswordField().setEchoChar('*');
    }

    private void addActionListeners() {
        loginView.getChangeLanguageEnglishButton().addActionListener(this);
        loginView.getChangeLanguageEnglishButton().setActionCommand("ENGLISH");

        loginView.getChangeLanguageRomanianButton().addActionListener(this);
        loginView.getChangeLanguageRomanianButton().setActionCommand("ROMANIAN");

        loginView.getChangeLanguageItalianButton().addActionListener(this);
        loginView.getChangeLanguageItalianButton().setActionCommand("ITALIAN");

        loginView.getLoginButton().addActionListener(this);
        loginView.getLoginButton().setActionCommand("LOGIN");

        loginView.getShowPasswordButton().addActionListener(this);
        loginView.getShowPasswordButton().setActionCommand("SHOW PASSWORD");

        loginView.getRegisterButton().addActionListener(this);
        loginView.getRegisterButton().setActionCommand("REGISTER");
    }

    public void processLoginAction() throws IOException, ClassNotFoundException {
        int responseInt = proxyClient.processLoginAction(loginView.getUsernameTextField().getText(), String.valueOf(loginView.getPasswordField().getPassword()));
        processLoginResponse(responseInt);
    }

    private void processLoginResponse(int responseInt) {
        if (responseInt == 250) {
            loginView.disposeWindow();
            EmployeeController employeeController = new EmployeeController(proxyClient);
        } else if (responseInt == 251) {
            loginView.disposeWindow();
            ManagerController managerController = new ManagerController(proxyClient);
        } else if (responseInt == 252) {
            loginView.disposeWindow();
            AdminController adminController = new AdminController(proxyClient);
        } else if (responseInt == 452 || responseInt == 461) {
            System.out.println("Wrong credentials");
            loginView.showWrongPassword();
        }
    }

    private User getUserWithUsername(String username) {
        return proxyClient.getUserWithUsername(username);
    }

    public List<User> getAllUsers() {
        return proxyClient.getUserList();
    }

    public void insertUser(User user) {
        proxyClient.insertUser(user);
    }

    public void processRegisterAction() {
        String username = loginView.getUsernameTextField().getText();
        String password = String.valueOf(loginView.getPasswordField().getPassword());
        User user = getUserWithUsername(username);
        for (Field field : User.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, User.class);
                if (propertyDescriptor.getReadMethod().invoke(user) != null) {
                    System.out.println("User with this username already exists");
                    return;
                }
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        List<User> userList = getAllUsers();
        String maxId = "-1";
        for (User currentUser : userList)
            if (Integer.parseInt(currentUser.getId()) > Integer.parseInt(maxId)) maxId = currentUser.getId();
        maxId = String.valueOf((Integer.parseInt(maxId) + 1));
        insertUser(new User(maxId, username, password, "employee", "", ""));
    }


    public Language getLanguage() {
        return language;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SHOW PASSWORD" -> {
                toggleShowPassword();
            }
            case "LOGIN" -> {
                try {
                    processLoginAction();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Failed to process login action");
                }
            }
            case "REGISTER" -> {
                processRegisterAction();
            }
            case "ENGLISH" -> {
                language.setSelectedLanguage(Languages.ENGLISH);
                addActionListeners();
            }
            case "ROMANIAN" -> {
                language.setSelectedLanguage(Languages.ROMANIAN);
                addActionListeners();
            }
            case "ITALIAN" -> {
                language.setSelectedLanguage(Languages.ITALIAN);
                addActionListeners();
            }
        }
    }
}
