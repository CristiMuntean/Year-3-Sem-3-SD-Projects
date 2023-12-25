package controller;

import model.User;
import model.language.Languages;
import model.language.UserFields;
import persistence.UserPersistence;
import view.AdminView;
import view.LoginView;
import view.OperationsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AdminController implements ActionListener {
    private static AdminController instance;
    private final UserPersistence userPersistence;
    private AdminView adminView;
    private String selectedRole;
    private Language language;

    private AdminController() {
        userPersistence = new UserPersistence();
    }

    public static AdminController getAdminControllerInstance(AdminView adminView) {
        if (instance == null)
            instance = new AdminController();
        instance.setAdminView(adminView);
        instance.language = Language.getLanguageInstance();
        instance.selectedRole = instance.language.getFields().getAdminFilterComboBox();
        return instance;
    }

    public List<User> getAllUsers() {
        return userPersistence.selectAllUsers();
    }

    public List<User> getAllUsersWithRole(String role) {
        return role.equals(language.getFields().getAdminFilterComboBox()) ? getAllUsers() : userPersistence.selectAllUsers().stream().filter(user -> user.getRole().equals(role)).collect(Collectors.toList());
    }

    public JTable getUsersTable(List<User> userList) {
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return userList.size() > 0 ? createUserTable(userList) : new JTable(tableModel);
    }

    private JTable createUserTable(List<User> userList) {
        if (userList.size() > 0) {
            String[] header = new String[UserFields.class.getDeclaredFields().length];
            header[0] = language.getFields().getUserFields().getId();
            header[1] = language.getFields().getUserFields().getUsername();
            header[2] = language.getFields().getUserFields().getPassword();
            header[3] = language.getFields().getUserFields().getRole();
            header[4] = language.getFields().getUserFields().getEmail();
            header[5] = language.getFields().getUserFields().getPhoneNumber();
            Object[][] data = new Object[userList.size() + 1][6];
            int j = 0;
            try {
                for (User user : userList) {
                    int k = 0;
                    for (Field field : user.getClass().getDeclaredFields()) {
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = null;
                        propertyDescriptor = new PropertyDescriptor(fieldName, user.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        data[j][k] = method.invoke(user);
                        k++;
                    }
                    j++;
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return new JTable(data, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        return null;
    }

    private String[] getTableHeader() {
        String[] headerList = new String[UserFields.class.getDeclaredFields().length];
        headerList[0] = language.getFields().getUserFields().getId();
        headerList[1] = language.getFields().getUserFields().getUsername();
        headerList[2] = language.getFields().getUserFields().getPassword();
        headerList[3] = language.getFields().getUserFields().getRole();
        headerList[4] = language.getFields().getUserFields().getEmail();
        headerList[5] = language.getFields().getUserFields().getPhoneNumber();
        return headerList;
    }

    public List<String> getDistinctTypes() {
        return getAllUsers().stream().map(user -> user.getRole()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "USER_OPERATIONS" -> {
                OperationsView<User> operationsView = new OperationsView<>(language.getFields().getAdminUserOperationsButton(), User.class, adminView);
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "FILTER" -> {
                setSelectedRole((String) adminView.getFilterComboBox().getSelectedItem());
                adminView.refreshPanel();
            }
            case "LOG_OUT" -> {
                adminView.disposeWindow();
                LoginController loginController = new LoginController();
            }
            case "ENGLISH" -> {
                language.setSelectedLanguage(Languages.ENGLISH);
            }
            case "ROMANIAN" -> {
                language.setSelectedLanguage(Languages.ROMANIAN);
            }
            case "ITALIAN" -> {
                language.setSelectedLanguage(Languages.ITALIAN);
            }
        }
    }

    public Language getLanguage() {
        return language;
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public void setSelectedRole(String filter) {
        this.selectedRole = filter;
    }

    public String getSelectedRole() {
        return selectedRole;
    }
}
