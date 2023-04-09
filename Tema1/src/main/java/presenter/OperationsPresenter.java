package presenter;


import model.*;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import persistence.ServiceLogPersistence;
import persistence.UserPersistence;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class OperationsPresenter<T> {
    private final Class<T> operationsClass;
    private final CarPersistence carPersistence;
    private final OwnerPersistence ownerPersistence;
    private final ServiceLogPersistence serviceLogPersistence;
    private final UserPersistence userPersistence;

    public OperationsPresenter(Class operationsClass) {
        this.operationsClass = operationsClass;
        this.carPersistence = new CarPersistence();
        this.ownerPersistence = new OwnerPersistence();
        this.serviceLogPersistence = new ServiceLogPersistence();
        this.userPersistence = new UserPersistence();
    }

    public void createOwnerPopUpView(String name, List<String> fieldNames, Owner selectedOwner, OperationsView<Owner> ownerOperationsView) {
        OwnerPopUpView ownerPopUpView = new OwnerPopUpView(name, fieldNames, selectedOwner, ownerOperationsView);
        ownerPopUpView.setVisible(true);
        ownerPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createCarPopUpView(String name, List<String> fieldNames, Car selectedCar, OperationsView<Car> carOperationsView) {
        CarPopUpView carPopUpView = new CarPopUpView(name, fieldNames, selectedCar, carOperationsView);
        carPopUpView.setVisible(true);
        carPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createServiceLogPopUpView(String name, List<String> fieldNames, ServiceLog selectedLog, OperationsView<ServiceLog> serviceLogOperationsView, UserInterface userInterface) {
        ServiceLogPopUpView serviceLogPopUpView = new ServiceLogPopUpView(name, fieldNames, selectedLog, serviceLogOperationsView, userInterface);
        serviceLogPopUpView.setVisible(true);
        serviceLogPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void createUserPopUpView(String name, List<String> fieldNames, User selectedUser, OperationsView<User> userOperationsView, UserInterface userInterface) {
        UserPopUpView userPopUpView = new UserPopUpView(name, fieldNames, selectedUser, userOperationsView, userInterface);
        userPopUpView.setVisible(true);
        userPopUpView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String prepareTitle() {
        return operationsClass.getSimpleName() + " operations";
    }

    public JTable getTable() {
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);

        switch (operationsClass.getSimpleName()) {
            case "Owner" -> {
                TableFactory<Owner> tableFactory = new TableFactory<>();
                List<Owner> ownersList = ownerPersistence.selectAllOwners();
                return ownersList.size() > 0 ? tableFactory.createTable(ownersList) : new JTable(tableModel);
            }
            case "Car" -> {
                TableFactory<Car> tableFactory = new TableFactory<>();
                List<Car> carList = carPersistence.selectAllCars();
                return carList.size() > 0 ? tableFactory.createTable(carList) : new JTable(tableModel);
            }
            case "ServiceLog" -> {
                TableFactory<CarJoinedOwner> tableFactory = new TableFactory<>();
                List<CarJoinedOwner> logList = carPersistence.selectAllCarsWithOwners();
                return logList.size() > 0 ? tableFactory.createTable(logList) : new JTable(tableModel);
            }
            case "User" -> {
                TableFactory<User> tableFactory = new TableFactory<>();
                List<User> userList = userPersistence.selectAllUsers();
                return userList.size() > 0 ? tableFactory.createTable(userList) : new JTable(tableModel);
            }
        }
        return null;
    }

    private String[] getTableHeader() {
        String[] headerList = new String[operationsClass.getDeclaredFields().length];
        int i = 0;
        for (Field field : operationsClass.getDeclaredFields()) {
            headerList[i] = field.getName();
            i++;
        }
        return headerList;
    }

    public void deleteCar(Car car) {
        carPersistence.deleteObject(car);
    }

    public void deleteOwner(Owner owner) {
        ownerPersistence.deleteObject(owner);
    }

    public void deleteUser(User user) {
        userPersistence.deleteObject(user);
    }

    public ServiceLog getServiceLogFromServiceLogNumber(String number) {
        return serviceLogPersistence.selectObject(number);
    }

    public void deleteServiceLog(ServiceLog serviceLog) {
        serviceLogPersistence.deleteObject(serviceLog);
    }
}
