package model.persistence;

import model.User;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence extends Persistence<User> {

    public List<User> selectAllUsers() {
        openConnection();
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String sqlQuery = "SELECT * FROM user;";
            stmt.executeQuery(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();
            List<User> users = createObjects(resultSet);
            closeConnection();
            return users;
        } catch (SQLException e) {
            System.out.println("Unable to execute login query.");
        }

        closeConnection();
        return null;
    }

    private List<String> getSelectedUserFields(User selectedServiceLog) {
        List<String> selectedUserFields = new ArrayList<>();
        for (Field field : User.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, User.class);
                Method method = propertyDescriptor.getReadMethod();
                selectedUserFields.add(fieldName + ":" + method.invoke(selectedServiceLog));
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return selectedUserFields;
    }

    public void updateUser(User user, User selectedUser) {
        List<String> filters = getSelectedUserFields(selectedUser);
        updateObject(user, filters);
    }

    @Override
    public User selectObject(String id) {
        openConnection();

        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String sqlQuery = "SELECT * FROM user WHERE username = \"" + id + "\";";
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            User value = createObject(resultSet);
            closeConnection();
            if (value.getUsername() == null)
                return null;
            return value;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve user");
        }
        closeConnection();
        return null;
    }
}
