package controller;

import model.Car;
import model.CarJoinedOwner;
import model.language.CarFields;
import persistence.CarPersistence;
import view.EmployeeInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEmployeeController {
    private String sortOptions;
    private String filterOptions;
    private EmployeeInterface employeeInterface;
    private final Language language = Language.getLanguageInstance();

    public String createSortOptions() {
        List<JCheckBox> sortCheckBoxes = employeeInterface.getSortCheckBoxes();
        if (sortCheckBoxes.get(0).isSelected() && sortCheckBoxes.get(1).isSelected())
            sortOptions = "Both";
        else if (sortCheckBoxes.get(0).isSelected())
            sortOptions = "Brand";
        else if (sortCheckBoxes.get(1).isSelected())
            sortOptions = "Fuel";
        else sortOptions = "None";
        return sortOptions;
    }

    public List<CarJoinedOwner> sortList(List<CarJoinedOwner> carsWithOwners) {
        switch (getSortOptions()) {
            case "Brand" -> {
                carsWithOwners.sort(new Comparator<CarJoinedOwner>() {
                    @Override
                    public int compare(CarJoinedOwner o1, CarJoinedOwner o2) {
                        return o1.getBrand().compareTo(o2.getBrand());
                    }
                });
            }
            case "Fuel" -> {
                carsWithOwners.sort(new Comparator<CarJoinedOwner>() {
                    @Override
                    public int compare(CarJoinedOwner o1, CarJoinedOwner o2) {
                        return o1.getFuelType().compareTo(o2.getFuelType());
                    }
                });
            }
            case "Both" -> {
                carsWithOwners.sort(new Comparator<CarJoinedOwner>() {
                    @Override
                    public int compare(CarJoinedOwner o1, CarJoinedOwner o2) {
                        int brandCompare = o1.getBrand().compareTo(o2.getBrand());
                        return brandCompare != 0 ? brandCompare : o1.getFuelType().compareTo(o2.getFuelType());
                    }
                });
            }
        }
        return carsWithOwners;
    }

    public void createFilteringOptions(List<JComboBox<String>> filterComboBoxes) {
        StringBuilder filterOptionsBuilder = new StringBuilder();
        if (!filterComboBoxes.get(0).getSelectedItem().equals(language.getFields().getFilterOwnerComboBox()))
            filterOptionsBuilder.append("Owner:").append(filterComboBoxes.get(0).getSelectedItem().toString().replace(" ", "_")).append(" ");
        if (!filterComboBoxes.get(1).getSelectedItem().equals(language.getFields().getFilterBrandComboBox()))
            filterOptionsBuilder.append("CarBrand:").append(filterComboBoxes.get(1).getSelectedItem()).append(" ");
        if (!filterComboBoxes.get(2).getSelectedItem().equals(language.getFields().getFilterFuelTypeComboBox()))
            filterOptionsBuilder.append("FuelType:").append(filterComboBoxes.get(2).getSelectedItem()).append(" ");
        if (!filterComboBoxes.get(3).getSelectedItem().equals(language.getFields().getFilterColorComboBox()))
            filterOptionsBuilder.append("Color:").append(filterComboBoxes.get(3).getSelectedItem()).append(" ");
        if (filterOptionsBuilder.toString().equals(""))
            filterOptions = "None";
        else filterOptions = filterOptionsBuilder.toString();
    }

    public List<Car> searchCarsByOwner(String name) {
        CarPersistence carPersistence = new CarPersistence();
        List<CarJoinedOwner> carJoinedOwners = carPersistence.selectAllCarsWithOwners();

        carJoinedOwners = carJoinedOwners.stream().filter(
                (car) -> car.getCarOwner().equals(name)
        ).toList();
        return carJoinedOwners.stream().map(
                carJoinedOwner -> carPersistence.selectObject(carJoinedOwner.getCarId())
        ).toList();
    }

    public JTable getCarsTable(List<Car> cars) {
        TableModel tableModel = new DefaultTableModel(getCarTableHeader(), 1);
        return cars.size() > 0 ? createCarTable(cars) : new JTable(tableModel);
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

    private String[] getCarTableHeader() {
        String[] headerList = new String[CarFields.class.getDeclaredFields().length];
        headerList[0] = language.getFields().getCarFields().getId();
        headerList[1] = language.getFields().getCarFields().getBrand();
        headerList[2] = language.getFields().getCarFields().getModelName();
        headerList[3] = language.getFields().getCarFields().getColor();
        headerList[4] = language.getFields().getCarFields().getFuelType();
        return headerList;
    }

    public List<CarJoinedOwner> filterList(List<CarJoinedOwner> carsWithOwners) {
        for (String element : getFilterOptions().split(" ")) {
            carsWithOwners = carsWithOwners.stream().filter(
                    (car) -> {
                        if (element.contains("Owner:")) {
                            return car.getCarOwner().equals(element.split(":")[1].replace("_", " "));
                        }
                        if (element.contains("CarBrand:")) {
                            return car.getBrand().equals(element.split(":")[1]);
                        }
                        if (element.contains("FuelType:")) {
                            return car.getFuelType().equals(element.split(":")[1]);
                        }
                        if (element.contains("Color:")) {
                            return car.getColor().equals(element.split(":")[1]);
                        }
                        return true;
                    }
            ).collect(Collectors.toList());
        }
        return carsWithOwners;
    }

    public String getSortOptions() {
        return sortOptions;
    }

    public void setSortOptions(String sortOptions) {
        this.sortOptions = sortOptions;
    }

    public String getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(String filterOptions) {
        this.filterOptions = filterOptions;
    }

    public void setEmployeeInterface(EmployeeInterface employeeInterface){
        this.employeeInterface = employeeInterface;
    }

    public EmployeeInterface getEmployeeInterface() {
        return employeeInterface;
    }
}
