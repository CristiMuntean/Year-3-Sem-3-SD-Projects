package presenter;

import model.CarJoinedOwner;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import view.EmployeeInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;

public class EmployeePresenter extends AbstractEmployeePresenter {
    private static EmployeePresenter instance;
    private final OwnerPersistence ownerPersistence;
    private final CarPersistence carPersistence;

    private EmployeePresenter() {
        setSortOptions("None");
        setFilterOptions("None");
        ownerPersistence = new OwnerPersistence();
        carPersistence = new CarPersistence();
    }

    public static EmployeePresenter getPresenterInstance(EmployeeInterface employeeInterface) {
        if (instance == null)
            instance = new EmployeePresenter();
        instance.setEmployeeInterface(employeeInterface);
        return instance;
    }

    public List<String> getDistinctOwners() {
        return ownerPersistence.selectDistinctOwnerNames();
    }

    public List<String> getDistinctCarBrands() {
        return carPersistence.selectDistinctColumn("brand");
    }

    public List<String> getDistinctFuelTypes() {
        return carPersistence.selectDistinctColumn("fuelType");
    }

    public List<String> getDistinctColors() {
        return carPersistence.selectDistinctColumn("color");
    }

    public List<CarJoinedOwner> getAllCarsWithOwners() {
        return carPersistence.selectAllCarsWithOwners();
    }

    public JTable getCarsWithOwnersTable(List<CarJoinedOwner> carsWithOwners) {
        TableFactory<CarJoinedOwner> tableFactory = new TableFactory<>();
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return carsWithOwners.size() > 0 ? tableFactory.createTable(carsWithOwners) : new JTable(tableModel);
    }

    private String[] getTableHeader() {
        String[] headerList = new String[CarJoinedOwner.class.getDeclaredFields().length];
        int i = 0;
        for (Field field : CarJoinedOwner.class.getDeclaredFields()) {
            headerList[i] = field.getName();
            i++;
        }
        return headerList;
    }
}
