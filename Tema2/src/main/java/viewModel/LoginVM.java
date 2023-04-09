package viewModel;


import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CommandInterface;
import viewModel.commands.LoginCommand;
import viewModel.commands.RegisterCommand;


public class LoginVM {
    private static LoginVM loginVM;
    private final CommandInterface loginCommand;
    private final CommandInterface registerCommand;
    private final Property<String> userField;
    private final Property<String> passField;
    private final Property<String> wrongPasswordField;

    public LoginVM() {
        userField = PropertyFactory.createProperty("username", this, String.class);
        passField = PropertyFactory.createProperty("pass", this, String.class);
        wrongPasswordField = PropertyFactory.createProperty("wrongPass", this, String.class);

        loginCommand = new LoginCommand(this);
        registerCommand = new RegisterCommand(this);
    }

    public static LoginVM initializeLoginVM() {
        if (loginVM == null)
            loginVM = new LoginVM();
        return loginVM;
    }

    public String getUserField() {
        return userField.get();
    }

    public void setUserField(String userField) {
        this.userField.set(userField);
    }

    public String getPassField() {
        return passField.get();
    }

    public void setPassField(String passField) {
        this.passField.set(passField);
    }

    public String getWrongPasswordField() {
        return wrongPasswordField.get();
    }

    public void setWrongPasswordField() {
        this.wrongPasswordField.set("Wrong username and password combination");
    }

    public CommandInterface getLoginCommand() {
        return loginCommand;
    }

    public CommandInterface getRegisterCommand() {
        return registerCommand;
    }
}
