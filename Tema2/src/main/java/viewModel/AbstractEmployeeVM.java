package viewModel;

import model.Car;
import model.CarJoinedOwner;
import model.persistence.CarPersistence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEmployeeVM {
    private String sortOptions;
    private String filterOptions;

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

    public JTable getCarsTable(List<Car> cars) {
        TableFactory<Car> tableFactory = new TableFactory<>();
        TableModel tableModel = new DefaultTableModel(getCarTableHeader(), 1);
        return cars.size() > 0 ? tableFactory.createTable(cars) : new JTable(tableModel);
    }

    private String[] getCarTableHeader() {
        String[] headerList = new String[Car.class.getDeclaredFields().length];
        int i = 0;
        for (Field field : Car.class.getDeclaredFields()) {
            headerList[i] = field.getName();
            i++;
        }
        return headerList;
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

}
