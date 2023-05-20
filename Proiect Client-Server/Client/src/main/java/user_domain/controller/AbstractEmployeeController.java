package user_domain.controller;

import car_domain.model.Car;
import car_domain.model.CarJoinedOwner;
import client.ProxyClient;
import user_domain.view.language.CarFields;
import user_domain.view.language.CarJoinedOwnerFields;
import user_domain.view.language.Language;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEmployeeController {
    private final Language language = Language.getLanguageInstance();
    private ProxyClient proxyClient;
    private String sortOptions;
    private String filterOptions;

    protected void saveInfoInTxtFormat(String format, List<Car> carList) {
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
        }
    }

    protected void saveInfoInXmlFormat(String format, List<Car> carList) {
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
        }
    }

    protected void saveInfoInJsonFormat(String format, List<Car> carList) {
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
        }
    }

    protected void saveInfoInCsvFormat(String format, List<Car> carList) {
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
        }
    }


    public JTable getCarsWithOwnersTable() {
        List<CarJoinedOwner> carWithOwners = getCarJoinedOwnersList();
        return carWithOwners.size() == 0 ? new JTable(new DefaultTableModel(getTableHeader(), 1)) :
                getCarsWithOwnersTable(carWithOwners);
    }

    public JTable getCarsWithOwnersTable(List<CarJoinedOwner> carsWithOwners) {
        TableModel tableModel = new DefaultTableModel(getTableHeader(), 1);
        return carsWithOwners.size() > 0 ? createCarWithOwnersTable(carsWithOwners) : new JTable(tableModel);
    }

    public JTable createCarWithOwnersTable(List<CarJoinedOwner> carsWithOwners) {
        if (carsWithOwners.size() > 0) {
            String[] header = getTableHeader();
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

    public String[] getTableHeader() {
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

    public String createSortOptions(List<JCheckBox> sortCheckBoxes) {
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

    public List<String> getDistinctOwners() {
        return proxyClient.getDistinctOwners();
    }

    public List<String> getDistinctCarBrands() {
        return proxyClient.getDistinctCarBrands();
    }

    public List<String> getDistinctFuelTypes() {
        return proxyClient.getDistinctFuelTypes();
    }

    public List<String> getDistinctColors() {
        return proxyClient.getDistinctColors();
    }

    protected List<CarJoinedOwner> getCarJoinedOwnersList() {
        return proxyClient.getCarJoinedOwnersList();
    }

    protected List<Car> getCarList() {
        return proxyClient.getCarList();
    }

    public List<Car> searchCarsByOwner(String name) {
        List<CarJoinedOwner> carJoinedOwners = getCarJoinedOwnersList();
        List<String> carIds = carJoinedOwners.stream().filter(carJoinedOwner -> carJoinedOwner.getCarOwner().equals(name)).map(CarJoinedOwner::getCarId).collect(Collectors.toList());
        List<Car> carList = getCarList();
        return carList.stream().filter(car -> carIds.contains(car.getId())).collect(Collectors.toList());
    }

    public JTable getCarsTable(List<Car> cars) {
        TableModel tableModel = new DefaultTableModel(getCarTableHeader(), 1);
        return cars.size() > 0 ? createCarTable(cars) : new JTable(tableModel);
    }

    private JTable createCarTable(List<Car> carList) {
        if (carList.size() > 0) {
            String[] header = getCarTableHeader();
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

    public ProxyClient getProxyClient() {
        return proxyClient;
    }

    public void setProxyClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }
}
