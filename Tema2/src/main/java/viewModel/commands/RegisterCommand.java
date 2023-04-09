package viewModel.commands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.LoginVM;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RegisterCommand implements CommandInterface {
    private final LoginVM loginVM;

    public RegisterCommand(LoginVM loginVM) {
        this.loginVM = loginVM;
    }

    @Override
    public boolean execute() {
        UserPersistence userPersistence = new UserPersistence();
        String username = loginVM.getUserField();
        String password = loginVM.getPassField();
        User user = userPersistence.selectObject(username);
        for (Field field : User.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, User.class);
                if (propertyDescriptor.getReadMethod().invoke(user) != null) {
                    System.out.println("User with this username already exists");
                    return false;
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
        return true;
    }
}
