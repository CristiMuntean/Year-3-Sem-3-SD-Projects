package controller;

import model.*;
import model.language.CarJoinedOwnerFields;
import model.language.Languages;
import model.language.UserFields;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import view.CarsPopUpView;
import view.EmployeeInterface;
import view.LoginView;
import view.OperationsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EmployeeController extends AbstractEmployeeController implements ActionListener {
    private static EmployeeController instance;
    private final OwnerPersistence ownerPersistence;
    private final CarPersistence carPersistence;
    private Language language;

    private EmployeeController() {
        setSortOptions("None");
        setFilterOptions("None");
        ownerPersistence = new OwnerPersistence();
        carPersistence = new CarPersistence();
    }

    public static EmployeeController getControllerInstance(EmployeeInterface employeeInterface) {
        if (instance == null)
            instance = new EmployeeController();
        instance.setEmployeeInterface(employeeInterface);
        instance.language = Language.getLanguageInstance();
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
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return carsWithOwners.size() > 0 ? createCarWithOwnersTable(carsWithOwners) : new JTable(tableModel);
    }

    private String[] getTableHeader() {
        String[] headerList = new String[CarJoinedOwnerFields.class.getDeclaredFields().length];
        headerList[0] = language.getFields().getCarJoinedOwnerFields().getServiceNumber();
        headerList[1] = language.getFields().getCarJoinedOwnerFields().getCarId();
        headerList[2] = language.getFields().getCarJoinedOwnerFields().getBrand();
        headerList[3] = language.getFields().getCarJoinedOwnerFields().getModelName();
        headerList[4] = language.getFields().getCarJoinedOwnerFields().getColor();
        headerList[5] = language.getFields().getCarJoinedOwnerFields().getFuelType();
        headerList[6] = language.getFields().getCarJoinedOwnerFields().getCnp();
        headerList[7] = language.getFields().getCarJoinedOwnerFields().getCarOwner();
        return headerList;
    }

    private JTable createCarWithOwnersTable(List<CarJoinedOwner> carsWithOwners) {
        if (carsWithOwners.size() > 0) {
            String[] header = new String[CarJoinedOwnerFields.class.getDeclaredFields().length];
            header[0] = language.getFields().getCarJoinedOwnerFields().getServiceNumber();
            header[1] = language.getFields().getCarJoinedOwnerFields().getCarId();
            header[2] = language.getFields().getCarJoinedOwnerFields().getBrand();
            header[3] = language.getFields().getCarJoinedOwnerFields().getModelName();
            header[4] = language.getFields().getCarJoinedOwnerFields().getColor();
            header[5] = language.getFields().getCarJoinedOwnerFields().getFuelType();
            header[6] = language.getFields().getCarJoinedOwnerFields().getCnp();
            header[7] = language.getFields().getCarJoinedOwnerFields().getCarOwner();
            Object[][] data = new Object[carsWithOwners.size() + 1][8];
            int j = 0;
            try {
                for (CarJoinedOwner carJoinedOwner : carsWithOwners) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SORT" -> {
                createSortOptions();
                getEmployeeInterface().clearPanel();
                getEmployeeInterface().reprepareGui();
            }
            case "FILTER" -> {
                createFilteringOptions(getEmployeeInterface().getFilterComboBoxes());
                getEmployeeInterface().clearPanel();
                getEmployeeInterface().reprepareGui();
            }
            case "OWNER_OPERATIONS" -> {
                OperationsView<Owner> operationsView = new OperationsView<>("Owner operations", Owner.class, getEmployeeInterface());
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "CAR_OPERATIONS" -> {
                OperationsView<Car> operationsView = new OperationsView<>("Car operations", Car.class, getEmployeeInterface());
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "SERVICE_OPERATIONS" -> {
                OperationsView<ServiceLog> serviceLogOperationsView = new OperationsView<>("Service operations", ServiceLog.class, getEmployeeInterface());
                serviceLogOperationsView.setVisible(true);
                serviceLogOperationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "SEARCH" -> {
                if(!getEmployeeInterface().getSearchComboBox().getSelectedItem().equals("Owner")) {
                    List<Car> cars = searchCarsByOwner((String) getEmployeeInterface().getSearchComboBox().getSelectedItem());
                    JTable carsTable = getCarsTable(cars);
                    CarsPopUpView carsPopUpView = new CarsPopUpView("Searched cars", carsTable);
                    carsPopUpView.setVisible(true);
                }
            }
            case "SAVE" -> {
                String format = getEmployeeInterface().getSaveComboBox().getSelectedItem().toString();
                CarPersistence carPersistence = new CarPersistence();
                List<Car> carList = carPersistence.selectAllCars();
                switch (format) {
                    case "csv" -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Id,brand,modelName,color,fuelType\n");
                        for (Car car : carList) {
                            sb.append(String.join(",",
                                    Arrays.stream(car.toString().split(",")).map(el -> el.split(":")[1]).collect(Collectors.toList()))).append("\n");
                        }
                        try {
                            File file = new File("car_info.csv");
                            file.createNewFile();
                            FileWriter fw = new FileWriter(file);
                            fw.write(sb.toString());
                            fw.close();
                        } catch (IOException ex) {
                            System.out.println("Something went wrong when trying to save to format " + format);
                            return;
                        }
                    }
                    case "json" -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append("{\n    \"carList\": {\n");
                        for (Car car : carList) {
                            String carJson = "        \"car\": {\n            " + String.join(",\n            ",
                                    Arrays.stream(car.toString().split(","))
                                            .map(el -> "\"" + el.split(":")[0] + "\": \"" + el.split(":")[1] + "\"").collect(Collectors.toList())) + "\n        },\n";
                            sb.append(carJson);
                        }
                        sb.deleteCharAt(sb.length() - 2);
                        sb.append("    }\n}");
                        try {
                            File file = new File("car_info.json");
                            file.createNewFile();
                            FileWriter fw = new FileWriter(file);
                            fw.write(sb.toString());
                            fw.close();
                        } catch (IOException ex) {
                            System.out.println("Something went wrong when trying to save to format " + format);
                            return;
                        }
                    }
                    case "xml" -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                        sb.append("<carList>\n");
                        for (Car car : carList) {
                            sb.append("    <car>\n");
                            String carXML = String.join("\n",
                                    Arrays.stream(car.toString().split(",")).map(el -> "        <" + el.split(":")[0] + ">" + el.split(":")[1] + "</" + el.split(":")[0] + ">").collect(Collectors.toList()));
                            sb.append(carXML).append("\n    </car>\n");
                        }
                        sb.append("</carList>");
                        try {
                            File file = new File("car_info.xml");
                            file.createNewFile();
                            FileWriter fw = new FileWriter(file);
                            fw.write(sb.toString());
                            fw.close();
                        } catch (IOException ex) {
                            System.out.println("Something went wrong when trying to save to format " + format);
                            return;
                        }
                    }
                    case "txt" -> {
                        StringBuilder sb = new StringBuilder();
                        for (Car car : carList) {
                            sb.append(car).append("\n");
                        }
                        try {
                            File file = new File("car_info.txt");
                            file.createNewFile();
                            FileWriter fw = new FileWriter(file);
                            fw.write(sb.toString());
                            fw.close();
                        } catch (IOException ex) {
                            System.out.println("Something went wrong when trying to save to format " + format);
                            return;
                        }
                    }
                }
            }
            case "LOG_OUT" -> {
                getEmployeeInterface().disposeWindow();
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

}
