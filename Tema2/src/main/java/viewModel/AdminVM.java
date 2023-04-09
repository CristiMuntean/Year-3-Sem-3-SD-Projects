package viewModel;

import model.User;
import model.persistence.UserPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CommandInterface;
import viewModel.commands.FilterCommand;
import viewModel.commands.LogOutCommand;
import viewModel.commands.UserOperationsCommand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class AdminVM {
    private static AdminVM instance;
    private final UserPersistence userPersistence;
    private String filterOptions = "Role";
    private final CommandInterface logOutCommand;
    private final CommandInterface userOperationsCommand;
    private final CommandInterface filterCommand;
    private final Property<DefaultComboBoxModel<String>> filterModel;
    private final Property<String> selectedFilter;

    private AdminVM() {
        userPersistence = new UserPersistence();

        filterModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedFilter = PropertyFactory.createProperty("selected", this, String.class);

        logOutCommand = new LogOutCommand();
        userOperationsCommand = new UserOperationsCommand();
        filterCommand = new FilterCommand(this);

        populateFields();
    }

    public static AdminVM getAdminVMInstance() {
        if (instance == null)
            instance = new AdminVM();
        return instance;
    }

    private void populateFields() {
        List<String> distinctTypeList = getDistinctTypes();
        distinctTypeList.add(0, "Role");

        filterModel.get().addAll(distinctTypeList);
        filterModel.get().setSelectedItem(filterModel.get().getElementAt(0));
    }

    public DefaultComboBoxModel<String> getFilterModel() {
        return filterModel.get();
    }

    public void setFilterModel(DefaultComboBoxModel<String> filterModel) {
        this.filterModel.set(filterModel);
    }

    public String getSelectedFilter() {
        return (String) this.filterModel.get().getSelectedItem();
    }

    public void setSelectedFilter(String selectedFilter) {
        this.filterModel.get().setSelectedItem(selectedFilter);
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

    public List<String> getDistinctTypes() {
        return getAllUsers().stream().map(user -> user.getRole()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
    }

    public List<User> filterList(List<User> userList) {
        return filterOptions.equals("Role") ? getAllUsers() : userList.stream().filter((user -> user.getRole().equals(filterOptions))).collect(Collectors.toList());
    }

    public String getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(String filterOptions) {
        this.filterOptions = filterOptions;
    }

    public CommandInterface getLogOutCommand() {
        return logOutCommand;
    }

    public CommandInterface getUserOperationsCommand() {
        return userOperationsCommand;
    }

    public CommandInterface getFilterCommand() {
        return filterCommand;
    }
}
