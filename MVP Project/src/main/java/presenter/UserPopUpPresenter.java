package presenter;

import model.User;
import persistence.UserPersistence;
import view.OperationsInterface;
import view.UserInterface;

public class UserPopUpPresenter {
    private static UserPopUpPresenter instance;
    private final UserPersistence userPersistence;
    private OperationsInterface<User> userOperationsInterface;
    private UserInterface userInterface;

    private UserPopUpPresenter(OperationsInterface<User> userOperationsInterface, UserInterface userInterface) {
        this.userPersistence = new UserPersistence();
        this.userOperationsInterface = userOperationsInterface;
        this.userInterface = userInterface;
    }

    public static UserPopUpPresenter getPresenterInterface(OperationsInterface<User> userOperationsInterface, UserInterface employeeInterface) {
        if (instance == null) {
            instance = new UserPopUpPresenter(userOperationsInterface, employeeInterface);
            instance.setUserOperationsInterface(userOperationsInterface);
            instance.setUserInterface(employeeInterface);
        }
        return instance;
    }

    public void insertUser(User user) {
        userPersistence.insertObject(user);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateUser(User user, User selectedUser) {
        userPersistence.updateUser(user, selectedUser);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }


    public void setUserOperationsInterface(OperationsInterface<User> userOperationsInterface) {
        this.userOperationsInterface = userOperationsInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}
