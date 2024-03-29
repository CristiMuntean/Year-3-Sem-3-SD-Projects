package car_domain.controller;


import car_domain.model.Car;
import car_domain.model.CarJoinedOwner;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import car_domain.view.CarPopUpView;
import car_domain.view.OperationsView;
import car_domain.view.OwnerPopUpView;
import car_domain.view.ServiceLogPopUpView;
import client.ProxyClient;
import user_domain.model.User;
import user_domain.view.UserInterface;
import user_domain.view.UserPopUpView;
import user_domain.view.language.*;

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

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class OperationsController<T> implements ActionListener {
    private final Class<T> operationsClass;
    private final OperationsView<T> operationsView;
    private final Language language;
    private final ProxyClient proxyClient;

    public OperationsController(Class operationsClass, OperationsView<T> operationsView, ProxyClient proxyClient) {
        this.operationsClass = operationsClass;
        this.operationsView = operationsView;
        this.language = Language.getLanguageInstance();
        this.proxyClient = proxyClient;
    }

    public void createOwnerPopUpView(String name, Owner selectedOwner, OperationsView<Owner> ownerOperationsView, ProxyClient client, UserInterface userInterface) {
        OwnerPopUpView ownerPopUpView = new OwnerPopUpView(name, selectedOwner, ownerOperationsView, client, userInterface);
        ownerPopUpView.setVisible(true);
        ownerPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createCarPopUpView(String name, Car selectedCar, OperationsView<Car> carOperationsView, ProxyClient client, UserInterface userInterface) {
        CarPopUpView carPopUpView = new CarPopUpView(name, selectedCar, carOperationsView, client, userInterface);
        carPopUpView.setVisible(true);
        carPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createServiceLogPopUpView(String name, ServiceLog selectedLog, OperationsView<ServiceLog> serviceLogOperationsView, UserInterface userInterface, ProxyClient client) {
        ServiceLogPopUpView serviceLogPopUpView = new ServiceLogPopUpView(name, selectedLog, serviceLogOperationsView, userInterface, client);
        serviceLogPopUpView.setVisible(true);
        serviceLogPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createUserPopUpView(String name, User selectedUser, OperationsView<User> userOperationsView, UserInterface userInterface, ProxyClient client) {
        UserPopUpView userPopUpView = new UserPopUpView(name, selectedUser, userOperationsView, userInterface, client);
        userPopUpView.setVisible(true);
        userPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String prepareTitle() {
        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                return language.getFields().getOwnerOperationsTitle();
            }
            case "Car" -> {
                return language.getFields().getCarOperationsTitle();
            }
            case "ServiceLog" -> {
                return language.getFields().getServiceOperationsTitle();
            }
            case "User" -> {
                return language.getFields().getUserOperationsTitle();
            }
        }
        return "";
    }

    private List<Car> getCarList() {
        return proxyClient.getCarList();
    }

    private List<Owner> getOwnerList() {
        return proxyClient.getOwnerList();
    }

    private List<CarJoinedOwner> getCarJoinedOwnersList() {
        return proxyClient.getCarJoinedOwnersList();
    }

    private List<User> getUserList() {
        return proxyClient.getUserList();
    }

    public ServiceLog getServiceLogFromServiceLogNumber(String number) {
        return proxyClient.getServiceLogFromServiceLogNumber(number);
    }

    public JTable getTable() {
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);

        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                List<Owner> ownersList = getOwnerList();
                return ownersList.size() > 0 ? createOwnerTable(ownersList) : new JTable(tableModel);
            }
            case "Car" -> {
                List<Car> carList = getCarList();
                return carList.size() > 0 ? createCarTable(carList) : new JTable(tableModel);
            }
            case "ServiceLog" -> {
                List<CarJoinedOwner> logList = getCarJoinedOwnersList();
                return logList.size() > 0 ? createServiceLogTable(logList) : new JTable(tableModel);
            }
            case "User" -> {
                List<User> userList = getUserList();
                return userList.size() > 0 ? createUserTable(userList) : new JTable(tableModel);
            }
        }
        return null;
    }

    private String[] getTableHeader() {
        String[] headerList = new String[1];
        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                headerList = new String[OwnerFields.class.getDeclaredFields().length];
                headerList[0] = language.getFields().getOwnerFields().getCnp();
                headerList[1] = language.getFields().getOwnerFields().getName();
                headerList[2] = language.getFields().getOwnerFields().getSurname();
            }
            case "Car" -> {
                headerList = new String[CarFields.class.getDeclaredFields().length];
                headerList[0] = language.getFields().getCarFields().getId();
                headerList[1] = language.getFields().getCarFields().getBrand();
                headerList[2] = language.getFields().getCarFields().getModelName();
                headerList[3] = language.getFields().getCarFields().getColor();
                headerList[4] = language.getFields().getCarFields().getFuelType();
            }
            case "ServiceLog" -> {
                headerList = new String[CarJoinedOwnerFields.class.getDeclaredFields().length];
                headerList[0] = language.getFields().getCarJoinedOwnerFields().getServiceNumber();
                headerList[1] = language.getFields().getCarJoinedOwnerFields().getCarId();
                headerList[2] = language.getFields().getCarJoinedOwnerFields().getBrand();
                headerList[3] = language.getFields().getCarJoinedOwnerFields().getModelName();
                headerList[4] = language.getFields().getCarJoinedOwnerFields().getColor();
                headerList[5] = language.getFields().getCarJoinedOwnerFields().getFuelType();
                headerList[6] = language.getFields().getCarJoinedOwnerFields().getCnp();
                headerList[7] = language.getFields().getCarJoinedOwnerFields().getCarOwner();
            }
            case "User" -> {
                headerList = new String[UserFields.class.getDeclaredFields().length];
                headerList[0] = language.getFields().getUserFields().getId();
                headerList[1] = language.getFields().getUserFields().getUsername();
                headerList[2] = language.getFields().getUserFields().getPassword();
                headerList[3] = language.getFields().getUserFields().getRole();
                headerList[4] = language.getFields().getUserFields().getEmail();
                headerList[5] = language.getFields().getUserFields().getPhoneNumber();

            }
        }
        return headerList;
    }

    private JTable createOwnerTable(List<Owner> ownerList) {
        if (ownerList.size() > 0) {
            String[] header = new String[OwnerFields.class.getDeclaredFields().length];
            header[0] = language.getFields().getOwnerFields().getCnp();
            header[1] = language.getFields().getOwnerFields().getName();
            header[2] = language.getFields().getOwnerFields().getSurname();
            Object[][] data = new Object[ownerList.size() + 1][3];
            int j = 0;
            try {
                for (Owner owner : ownerList) {
                    int k = 0;
                    for (Field field : owner.getClass().getDeclaredFields()) {
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = null;
                        propertyDescriptor = new PropertyDescriptor(fieldName, owner.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        data[j][k] = method.invoke(owner);
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

    private JTable createCarTable(List<Car> carList) {
        if (carList.size() > 0) {
            String[] header = new String[CarFields.class.getDeclaredFields().length];
            header[0] = language.getFields().getCarFields().getId();
            header[1] = language.getFields().getCarFields().getBrand();
            header[2] = language.getFields().getCarFields().getModelName();
            header[3] = language.getFields().getCarFields().getColor();
            header[4] = language.getFields().getCarFields().getFuelType();
            Object[][] data = new Object[carList.size() + 1][5];
            int j = 0;
            try {
                for (Car car : carList) {
                    int k = 0;
                    for (Field field : car.getClass().getDeclaredFields()) {
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = null;
                        propertyDescriptor = new PropertyDescriptor(fieldName, car.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        data[j][k] = method.invoke(car);
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

    public String prepareAddButton() {
        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                return language.getFields().getOwnerOperationsAddButton();
            }
            case "Car" -> {
                return language.getFields().getCarOperationsAddButton();
            }
            case "ServiceLog" -> {
                return language.getFields().getServiceOperationsAddButton();
            }
            case "User" -> {
                return language.getFields().getUserOperationsAddButton();
            }
        }
        return "";
    }

    public String prepareEditButton() {
        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                return language.getFields().getOwnerOperationsEditButton();
            }
            case "Car" -> {
                return language.getFields().getCarOperationsEditButton();
            }
            case "ServiceLog" -> {
                return language.getFields().getServiceOperationsEditButton();
            }
            case "User" -> {
                return language.getFields().getUserOperationsEditButton();
            }
        }
        return "";
    }

    public String prepareDeleteButton() {
        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                return language.getFields().getOwnerOperationsDeleteButton();
            }
            case "Car" -> {
                return language.getFields().getCarOperationsDeleteButton();
            }
            case "ServiceLog" -> {
                return language.getFields().getServiceOperationsDeleteButton();
            }
            case "User" -> {
                return language.getFields().getUserOperationsDeleteButton();
            }
        }
        return "";
    }


    private JTable createServiceLogTable(List<CarJoinedOwner> carJoinedOwners) {
        if (carJoinedOwners.size() > 0) {
            String[] header = new String[CarJoinedOwnerFields.class.getDeclaredFields().length];
            header[0] = language.getFields().getCarJoinedOwnerFields().getServiceNumber();
            header[1] = language.getFields().getCarJoinedOwnerFields().getCarId();
            header[2] = language.getFields().getCarJoinedOwnerFields().getBrand();
            header[3] = language.getFields().getCarJoinedOwnerFields().getModelName();
            header[4] = language.getFields().getCarJoinedOwnerFields().getColor();
            header[5] = language.getFields().getCarJoinedOwnerFields().getFuelType();
            header[6] = language.getFields().getCarJoinedOwnerFields().getCnp();
            header[7] = language.getFields().getCarJoinedOwnerFields().getCarOwner();
            Object[][] data = new Object[carJoinedOwners.size() + 1][8];
            int j = 0;
            try {
                for (CarJoinedOwner carJoinedOwner : carJoinedOwners) {
                    int k = 0;
                    for (Field field : carJoinedOwner.getClass().getDeclaredFields()) {
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = null;
                        propertyDescriptor = new PropertyDescriptor(fieldName, carJoinedOwner.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        data[j][k] = method.invoke(carJoinedOwner);
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

    public JTable createUserTable(List<User> userList) {
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

    public void deleteCar(Car car) {
        proxyClient.deleteCar(car);
    }

    public void deleteOwner(Owner owner) {
        proxyClient.deleteOwner(owner);
    }

    public void deleteUser(User user) {
        proxyClient.deleteUser(user);
    }

    public void deleteServiceLog(ServiceLog serviceLog) {
        proxyClient.deleteServiceLog(serviceLog);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "ADD" -> {
                if (operationsView.getOperationClass().getSimpleName().equals("Owner")) {
                    createOwnerPopUpView(language.getFields().getOwnerOperationsAddButton(),
                            null, (OperationsView<Owner>) operationsView, proxyClient, operationsView.getUserInterface());
                } else if (operationsView.getOperationClass().getSimpleName().equals("Car")) {
                    createCarPopUpView(language.getFields().getCarOperationsAddButton(),
                            null, (OperationsView<Car>) operationsView, proxyClient, operationsView.getUserInterface());
                } else if (operationsView.getOperationClass().getSimpleName().equals("ServiceLog")) {
                    createServiceLogPopUpView(language.getFields().getServiceOperationsAddButton(),
                            null, (OperationsView<ServiceLog>) operationsView, operationsView.getUserInterface(), proxyClient);
                } else if (operationsView.getOperationClass().getSimpleName().equals("User")) {
                    createUserPopUpView(language.getFields().getUserOperationsAddButton(),
                            null, (OperationsView<User>) operationsView, operationsView.getUserInterface(), proxyClient);
                }
            }
            case "UPDATE" -> {
                int row = operationsView.getOperationsTable().getSelectedRow();
                if (operationsView.getOperationClass().getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2));
                    createOwnerPopUpView(language.getFields().getOwnerOperationsEditButton(),
                            owner, (OperationsView<Owner>) operationsView, proxyClient, operationsView.getUserInterface());
                } else if (operationsView.getOperationClass().getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2),
                            (String) operationsView.getOperationsTable().getValueAt(row, 3),
                            (String) operationsView.getOperationsTable().getValueAt(row, 4));
                    createCarPopUpView(language.getFields().getCarOperationsEditButton(),
                            car, (OperationsView<Car>) operationsView, proxyClient, operationsView.getUserInterface());
                } else if (operationsView.getOperationClass().getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = getServiceLogFromServiceLogNumber((String) operationsView.getOperationsTable().getValueAt(row, 0));
                    createServiceLogPopUpView(language.getFields().getServiceOperationsEditButton(),
                            serviceLog, (OperationsView<ServiceLog>) operationsView,
                            operationsView.getUserInterface(), proxyClient);
                } else if (operationsView.getOperationClass().getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2),
                            (String) operationsView.getOperationsTable().getValueAt(row, 3),
                            (String) operationsView.getOperationsTable().getValueAt(row, 4),
                            (String) operationsView.getOperationsTable().getValueAt(row, 5)
                    );
                    createUserPopUpView(language.getFields().getUserOperationsEditButton(),
                            user, (OperationsView<User>) operationsView,
                            operationsView.getUserInterface(), proxyClient);
                }
            }
            case "DELETE" -> {
                int row = operationsView.getOperationsTable().getSelectedRow();
                if (operationsView.getOperationClass().getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2));
                    deleteOwner(owner);
                } else if (operationsView.getOperationClass().getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2),
                            (String) operationsView.getOperationsTable().getValueAt(row, 3),
                            (String) operationsView.getOperationsTable().getValueAt(row, 4));
                    deleteCar(car);
                } else if (operationsView.getOperationClass().getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = getServiceLogFromServiceLogNumber((String) operationsView.getOperationsTable().getValueAt(row, 0));
                    deleteServiceLog(serviceLog);
                } else if (operationsView.getOperationClass().getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsView.getOperationsTable().getValueAt(row, 0),
                            (String) operationsView.getOperationsTable().getValueAt(row, 1),
                            (String) operationsView.getOperationsTable().getValueAt(row, 2),
                            (String) operationsView.getOperationsTable().getValueAt(row, 3),
                            (String) operationsView.getOperationsTable().getValueAt(row, 4),
                            (String) operationsView.getOperationsTable().getValueAt(row, 5)
                    );
                    deleteUser(user);
                }
                operationsView.refreshPanel();
                operationsView.getUserInterface().refreshPanel();
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
}
