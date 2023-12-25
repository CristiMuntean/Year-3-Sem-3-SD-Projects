package viewModel.commands;

import model.User;
import model.persistence.UserPersistence;
import view.AdminView;
import view.EmployeeView;
import view.ManagerView;
import viewModel.LoginVM;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginCommand implements CommandInterface {
    private final LoginVM loginVM;

    public LoginCommand(LoginVM loginVM) {
        this.loginVM = loginVM;
    }

    @Override
    public boolean execute() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginVM.getUserField();
        User loggedUser = userPersistence.selectObject(username);
        if (loggedUser == null) {
            loginVM.setWrongPasswordField();
            loginVM.setUserField("");
            loginVM.setPassField("");
            return false;
        }
        switch (loggedUser.getRole()) {
            case "employee" -> {
                EmployeeView employeeView = new EmployeeView("Employee window");
                employeeView.setVisible(true);
                employeeView.setDefaultCloseOperation(EXIT_ON_CLOSE);

            }
            case "manager" -> {
                ManagerView managerView = new ManagerView("Manager window");
                managerView.setVisible(true);
                managerView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
            case "admin" -> {
                AdminView adminView = new AdminView("Admin window");
                adminView.setVisible(true);
                adminView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        }
        return true;
    }
}
