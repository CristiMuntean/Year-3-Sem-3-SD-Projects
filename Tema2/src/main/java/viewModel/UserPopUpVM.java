package viewModel;

import model.User;
import model.persistence.UserPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CommandInterface;
import viewModel.commands.UserCommandInsert;
import viewModel.commands.UserCommandUpdate;

public class UserPopUpVM {
    private static UserPopUpVM instance;
    private final UserPersistence userPersistence;
    private final Property<String> idTextField;
    private final Property<String> usernameTextField;
    private final Property<String> passwordTextField;
    private final Property<String> roleTextField;

    private final CommandInterface userCommandInsert;
    private final CommandInterface userCommandUpdate;

    private User selectedUser;

    private UserPopUpVM() {
        this.userPersistence = new UserPersistence();

        idTextField = PropertyFactory.createProperty("id", this, String.class);
        usernameTextField = PropertyFactory.createProperty("username", this, String.class);
        passwordTextField = PropertyFactory.createProperty("password", this, String.class);
        roleTextField = PropertyFactory.createProperty("role", this, String.class);

        userCommandInsert = new UserCommandInsert(this);
        userCommandUpdate = new UserCommandUpdate(this);
    }

    public static UserPopUpVM getVMInterface() {
        if (instance == null) {
            instance = new UserPopUpVM();
        }
        return instance;
    }

    public void insertUser(User user) {
        userPersistence.insertObject(user);
    }

    public void updateUser(User user, User selectedUser) {
        userPersistence.updateUser(user, selectedUser);
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getIdTextField() {
        return idTextField.get();
    }

    public void setIdTextField(String idTextField) {
        this.idTextField.set(idTextField);
    }

    public String getUsernameTextField() {
        return usernameTextField.get();
    }

    public void setUsernameTextField(String usernameTextField) {
        this.usernameTextField.set(usernameTextField);
    }

    public String getPasswordTextField() {
        return passwordTextField.get();
    }

    public void setPasswordTextField(String passwordTextField) {
        this.passwordTextField.set(passwordTextField);
    }

    public String getRoleTextField() {
        return roleTextField.get();
    }

    public void setRoleTextField(String roleTextField) {
        this.roleTextField.set(roleTextField);
    }

    public CommandInterface getUserCommandInsert() {
        return userCommandInsert;
    }

    public CommandInterface getUserCommandUpdate() {
        return userCommandUpdate;
    }
}
