package presenter;

import model.CarJoinedOwner;
import org.jetbrains.annotations.NotNull;
import view.EmployeeInterface;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEmployeePresenter {
    private String sortOptions;
    private String filterOptions;
    private EmployeeInterface employeeInterface;

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
        if (!filterComboBoxes.get(0).getSelectedItem().equals("Owner"))
            filterOptionsBuilder.append("Owner:").append(filterComboBoxes.get(0).getSelectedItem().toString().replace(" ", "_")).append(" ");
        if (!filterComboBoxes.get(1).getSelectedItem().equals("Car Brand"))
            filterOptionsBuilder.append("CarBrand:").append(filterComboBoxes.get(1).getSelectedItem()).append(" ");
        if (!filterComboBoxes.get(2).getSelectedItem().equals("Fuel Type"))
            filterOptionsBuilder.append("FuelType:").append(filterComboBoxes.get(2).getSelectedItem()).append(" ");
        if (!filterComboBoxes.get(3).getSelectedItem().equals("Color"))
            filterOptionsBuilder.append("Color:").append(filterComboBoxes.get(3).getSelectedItem()).append(" ");
        if (filterOptionsBuilder.toString().equals(""))
            filterOptions = "None";
        else filterOptions = filterOptionsBuilder.toString();
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
}
