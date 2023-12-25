package user_domain.controller;

import car_domain.view.OperationsInterface;
import client.ProxyClient;
import user_domain.controller.notifier.NotifierFacade;
import user_domain.model.User;
import user_domain.view.UserInterface;
import user_domain.view.UserPopUpView;
import user_domain.view.language.Language;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class UserPopUpController implements ActionListener {
    private static UserPopUpController instance;
    private OperationsInterface<User> userOperationsInterface;
    private UserInterface userInterface;
    private UserPopUpView userPopUpView;
    private Language language;
    private ProxyClient proxyClient;

    private UserPopUpController(OperationsInterface<User> userOperationsInterface, UserInterface userInterface) {
        this.userOperationsInterface = userOperationsInterface;
        this.userInterface = userInterface;
    }

    public static UserPopUpController getControllerInterface(OperationsInterface<User> userOperationsInterface, UserInterface employeeInterface, UserPopUpView userPopUpView, ProxyClient client) {
        if (instance == null) {
            instance = new UserPopUpController(userOperationsInterface, employeeInterface);
        }
        instance.setUserOperationsInterface(userOperationsInterface);
        instance.setUserInterface(employeeInterface);
        instance.setUserPopUpView(userPopUpView);
        instance.setProxyClient(client);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    private void sendInsertUserRequest(User user) {
        proxyClient.insertUser(user);
    }

    public void sendUpdateUserRequest(User newUser, User oldUser) {
        proxyClient.updateUser(newUser, oldUser);
    }

    public void insertUser(User user) {
        sendInsertUserRequest(user);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateUser(User user, User selectedUser) {
        sendUpdateUserRequest(user, selectedUser);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }


    public void setUserOperationsInterface(OperationsInterface<User> userOperationsInterface) {
        this.userOperationsInterface = userOperationsInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User(
                userPopUpView.getIdTextField().getText(),
                userPopUpView.getUsernameTextField().getText(),
                userPopUpView.getPasswordTextField().getText(),
                userPopUpView.getRoleTextField().getText(),
                userPopUpView.getEmailTextField().getText(),
                userPopUpView.getPhoneNumberTextField().getText()
        );

        if (e.getActionCommand().contains("Add")) {
            if (isNullOrEmpty(userPopUpView.getIdTextField().getText())) return;
            if (isNullOrEmpty(userPopUpView.getUsernameTextField().getText())) return;
            if (isNullOrEmpty(userPopUpView.getPasswordTextField().getText())) return;
            if (isNullOrEmpty(userPopUpView.getRoleTextField().getText())) return;
            insertUser(user);
        } else {
            User selectedUser = userPopUpView.getSelectedUser();
            if (user.getId().equals("")) user.setId(selectedUser.getId());
            if (user.getUsername().equals("")) user.setUsername(selectedUser.getUsername());
            if (user.getPassword().equals("")) user.setPassword(selectedUser.getPassword());
            if (user.getRole().equals("")) user.setRole(selectedUser.getRole());
            if (user.getEmail().equals("")) user.setEmail(selectedUser.getEmail());
            if (user.getPhoneNumber().equals("")) user.setPhoneNumber(selectedUser.getPhoneNumber());

            updateUser(user, selectedUser);
            NotifierFacade notifierFacade = new NotifierFacade();
            notifierFacade.notifyUserViaAvailableMeans(selectedUser, user);
        }
        userPopUpView.dispose();
    }

    public Language getLanguage() {
        return language;
    }

    public void setUserPopUpView(UserPopUpView userPopUpView) {
        this.userPopUpView = userPopUpView;
    }

    public void setProxyClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }
}
