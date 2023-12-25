package presenter;

import model.User;
import persistence.UserPersistence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;

public class AdminPresenter {
    private static AdminPresenter instance;
    private final UserPersistence userPersistence;

    private AdminPresenter() {
        userPersistence = new UserPersistence();
    }

    public static AdminPresenter getAdminPresenterInstance() {
        if (instance == null)
            instance = new AdminPresenter();
        return instance;
    }

    public List<User> getAllUsers() {
        return userPersistence.selectAllUsers();
    }

    public JTable getUsersTable(List<User> userList) {
        TableFactory<User> tableFactory = new TableFactory<>();
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return userList.size() > 0 ? tableFactory.createTable(userList) : new JTable(tableModel);
    }

    private String[] getTableHeader() {
        String[] headerList = new String[User.class.getDeclaredFields().length];
        int i = 0;
        for (Field field : User.class.getDeclaredFields()) {
            headerList[i] = field.getName();
            i++;
        }
        return headerList;
    }
}
