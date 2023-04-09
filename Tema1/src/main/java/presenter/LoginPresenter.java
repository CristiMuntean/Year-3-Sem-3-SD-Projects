package presenter;


import model.User;
import persistence.UserPersistence;
import view.AdminView;
import view.EmployeeView;
import view.LoginInterface;
import view.ManagerView;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginPresenter {
    private boolean showPassword;
    private LoginInterface loginInterface;
    private static LoginPresenter loginPresenter;

    private LoginPresenter() {
        showPassword = false;
    }

    public static LoginPresenter initializeLoginPresenter(LoginInterface loginInterface) {
        if (loginPresenter == null)
            loginPresenter = new LoginPresenter();
        loginPresenter.setLoginInterface(loginInterface);
        return loginPresenter;
    }

    public void toggleShowPassword() {
        showPassword = !showPassword;
        if (showPassword) loginInterface.getPasswordField().setEchoChar((char) 0);
        else loginInterface.getPasswordField().setEchoChar('*');
    }

    public void processLoginAction() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginInterface.getUsernameTextField().getText();
        User loggedUser = userPersistence.selectObject(username);
        if(loggedUser == null) {
            loginInterface.showWrongPassword();
            return;
        }
        loginInterface.disposeWindow();
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
    }

    public void processRegisterAction() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginInterface.getUsernameTextField().getText();
        String password = String.valueOf(loginInterface.getPasswordField().getPassword());
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
        userPersistence.insertObject(new User(maxId, username, password, "employee"));
    }

    public void setLoginInterface(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

}
