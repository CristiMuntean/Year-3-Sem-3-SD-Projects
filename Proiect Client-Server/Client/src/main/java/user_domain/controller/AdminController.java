package user_domain.controller;

import car_domain.view.OperationsView;
import client.ProxyClient;
import user_domain.model.User;
import user_domain.view.AdminView;
import user_domain.view.language.Language;
import user_domain.view.language.Languages;
import user_domain.view.language.UserFields;

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
    private AdminView adminView;
    private String selectedRole;
    private final Language language;
    private final ProxyClient proxyClient;

    public AdminController(ProxyClient proxyClient) {
        language = Language.getLanguageInstance();
        selectedRole = language.getFields().getAdminFilterComboBox();
        this.proxyClient = proxyClient;
        adminView = new AdminView(language.getFields().getAdminTitleLabel(), this, getUsersTable());
        adminView.setVisible(true);
        adminView.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public List<User> getAllUsers() {
        return proxyClient.getUserList();
    }

    public List<User> getAllUsersWithRole(String role) {
        return role.equals(language.getFields().getAdminFilterComboBox()) ? getAllUsers() :
                getAllUsers().stream()
                        .filter(user -> user.getRole().equals(role)).collect(Collectors.toList());
    }

    public JTable getUsersTable() {
        List<User> userList = getAllUsers();
        return getUsersTable(userList);
    }

    public JTable getUsersTable(List<User> userList) {
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return userList.size() > 0 ? createUserTable(userList) : new JTable(tableModel);
    }

    private JTable createUserTable(List<User> userList) {
        if (userList.size() > 0) {
            String[] header = getTableHeader();
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
                OperationsView<User> operationsView = new OperationsView<>(language.getFields().getAdminUserOperationsButton(), User.class, adminView, proxyClient);
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "FILTER" -> {
                setSelectedRole((String) adminView.getFilterComboBox().getSelectedItem());
                List<User> usersWithRole = getAllUsersWithRole(selectedRole);
                JTable usersTable = getUsersTable(usersWithRole);
                adminView.refreshPanel(usersTable, selectedRole);
            }
            case "LOG_OUT" -> {
                adminView.disposeWindow();
                LoginController loginController = new LoginController(proxyClient);
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

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String filter) {
        this.selectedRole = filter;
    }
}
