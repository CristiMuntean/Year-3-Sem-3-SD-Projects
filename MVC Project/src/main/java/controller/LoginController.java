package controller;


import model.User;
import model.language.Languages;
import persistence.UserPersistence;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginController implements ActionListener {
    private boolean showPassword;
    private LoginView loginView;
    private Language language;

    public LoginController() {
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

    public void processLoginAction() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginView.getUsernameTextField().getText();
        String password = String.valueOf(loginView.getPasswordField().getPassword());
        User loggedUser = userPersistence.selectUser(username, password);
        if (loggedUser == null) {
            loginView.showWrongPassword();
            return;
        }
        loginView.disposeWindow();
        switch (loggedUser.getRole()) {
            case "employee" -> {
                EmployeeView employeeView = new EmployeeView(language.getFields().getEmployeeTitleLabel());
                employeeView.setVisible(true);
                employeeView.setDefaultCloseOperation(EXIT_ON_CLOSE);

            }
            case "manager" -> {
                ManagerView managerView = new ManagerView(language.getFields().getManagerTitleLabel());
                managerView.setVisible(true);
                managerView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
            case "admin" -> {
                AdminView adminView = new AdminView(language.getFields().getAdminTitleLabel());
                adminView.setVisible(true);
                adminView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        }
    }

    public void processRegisterAction() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginView.getUsernameTextField().getText();
        String password = String.valueOf(loginView.getPasswordField().getPassword());
        User user = userPersistence.selectObject(username);
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
        List<User> userList = userPersistence.selectAllUsers();
        String maxId = "-1";
        for (User currentUser : userList)
            if (Integer.parseInt(currentUser.getId()) > Integer.parseInt(maxId)) maxId = currentUser.getId();
        maxId = String.valueOf((Integer.parseInt(maxId) + 1));
        userPersistence.insertObject(new User(maxId, username, password, "employee","",""));
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
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
                processLoginAction();
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
