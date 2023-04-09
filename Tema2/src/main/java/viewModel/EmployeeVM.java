package viewModel;

import model.CarJoinedOwner;
import model.persistence.CarPersistence;
import model.persistence.OwnerPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EmployeeVM extends AbstractEmployeeVM {
    private static EmployeeVM instance;
    private final OwnerPersistence ownerPersistence;
    private final CarPersistence carPersistence;
    private final Property<Boolean> sortBrandField;
    private final Property<Boolean> sortFuelField;
    private final Property<DefaultComboBoxModel<String>> filterOwnerModel;
    private final Property<String> selectedFilterOwnerField;
    private final Property<DefaultComboBoxModel<String>> filterBrandModel;
    private final Property<String> selectedFilterBrandField;
    private final Property<DefaultComboBoxModel<String>> filterFuelModel;
    private final Property<String> selectedFilterFuelField;
    private final Property<DefaultComboBoxModel<String>> filterColorModel;
    private final Property<String> selectedFilterColorField;
    private final Property<DefaultComboBoxModel<String>> searchModel;
    private final Property<String> selectedSearchField;
    private final Property<DefaultComboBoxModel<String>> saveModel;
    private final Property<String> selectedSaveField;

    private final CommandInterface logOutCommand;
    private final CommandInterface saveCommand;
    private final CommandInterface serviceOperationsCommand;
    private final CommandInterface carOperationsCommand;
    private final CommandInterface ownerOperationsCommand;
    private final CommandInterface searchCarsCommand;
    private final CommandInterface filteringOptionsCommand;
    private final CommandInterface sortOptionsCommand;

    private EmployeeVM() {
        setSortOptions("None");
        setFilterOptions("None");
        ownerPersistence = new OwnerPersistence();
        carPersistence = new CarPersistence();

        sortBrandField = PropertyFactory.createProperty("selected", this, Boolean.class, false);
        sortFuelField = PropertyFactory.createProperty("selected", this, Boolean.class, false);
        filterOwnerModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedFilterOwnerField = PropertyFactory.createProperty("selected", this, String.class);
        filterBrandModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedFilterBrandField = PropertyFactory.createProperty("selected", this, String.class);
        filterFuelModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedFilterFuelField = PropertyFactory.createProperty("selected", this, String.class);
        filterColorModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedFilterColorField = PropertyFactory.createProperty("selected", this, String.class);
        searchModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedSearchField = PropertyFactory.createProperty("selected", this, String.class);
        saveModel = PropertyFactory.createProperty("model", this, new DefaultComboBoxModel<>());
        selectedSaveField = PropertyFactory.createProperty("selected", this, String.class);

        logOutCommand = new LogOutCommand();
        saveCommand = new SaveCommand(this);
        serviceOperationsCommand = new ServiceOperationsCommand();
        carOperationsCommand = new CarOperationsCommand();
        ownerOperationsCommand = new OwnerOperationsCommand();
        searchCarsCommand = new SearchCarsCommand(this);
        filteringOptionsCommand = new FilteringOptionsCommand(this);
        sortOptionsCommand = new SortOptionsCommand(this);

        populateFields();
    }

    public static EmployeeVM getVMInstance() {
        if (instance == null)
            instance = new EmployeeVM();
        return instance;
    }

    private void populateFields() {
        List<String> distinctOwners = getDistinctOwners();
        distinctOwners.add(0, "Owner");
        filterOwnerModel.get().addAll(distinctOwners);
        filterOwnerModel.get().setSelectedItem(filterOwnerModel.get().getElementAt(0));

        List<String> distinctBrands = getDistinctCarBrands();
        distinctBrands.add(0, "Car Brand");
        filterBrandModel.get().addAll(distinctBrands);
        filterBrandModel.get().setSelectedItem(filterBrandModel.get().getElementAt(0));

        List<String> distinctFuelTypes = getDistinctFuelTypes();
        distinctFuelTypes.add(0, "Fuel Type");
        filterFuelModel.get().addAll(distinctFuelTypes);
        filterFuelModel.get().setSelectedItem(filterFuelModel.get().getElementAt(0));

        List<String> distinctColors = getDistinctColors();
        distinctColors.add(0, "Color");
        filterColorModel.get().addAll(distinctColors);
        filterColorModel.get().setSelectedItem(filterColorModel.get().getElementAt(0));

        searchModel.get().addAll(distinctOwners);
        searchModel.get().setSelectedItem(searchModel.get().getElementAt(0));

        saveModel.get().addAll(Arrays.asList("csv", "json", "xml", "txt"));
        saveModel.get().setSelectedItem(saveModel.get().getElementAt(0));
    }

    public Boolean getSortBrandField() {
        return sortBrandField.get();
    }

    public void setSortBrandField(Boolean sortBrandField) {
        this.sortBrandField.set(sortBrandField);
    }

    public Boolean getSortFuelField() {
        return sortFuelField.get();
    }

    public void setSortFuelField(Boolean sortFuelField) {
        this.sortFuelField.set(sortFuelField);
    }

    public DefaultComboBoxModel<String> getFilterOwnerModel() {
        return filterOwnerModel.get();
    }

    public void setFilterOwnerModel(DefaultComboBoxModel<String> filterOwnerModel) {
        this.filterOwnerModel.set(filterOwnerModel);
    }

    public String getSelectedFilterOwnerField() {
        return (String) filterOwnerModel.get().getSelectedItem();
    }

    public void setSelectedFilterOwnerField(String selectedFilterOwnerField) {
        this.filterOwnerModel.get().setSelectedItem(selectedFilterOwnerField);
    }

    public DefaultComboBoxModel<String> getFilterBrandModel() {
        return filterBrandModel.get();
    }

    public void setFilterBrandModel(DefaultComboBoxModel<String> filterBrandModel) {
        this.filterBrandModel.set(filterBrandModel);
    }

    public String getSelectedFilterBrandField() {
        return (String) filterBrandModel.get().getSelectedItem();
    }

    public void setSelectedFilterBrandField(String selectedFilterBrandField) {
        this.filterBrandModel.get().setSelectedItem(selectedFilterBrandField);
    }

    public DefaultComboBoxModel<String> getFilterFuelModel() {
        return filterFuelModel.get();
    }

    public void setFilterFuelModel(DefaultComboBoxModel<String> filterFuelModel) {
        this.filterFuelModel.set(filterFuelModel);
    }

    public String getSelectedFilterFuelField() {
        return (String) filterFuelModel.get().getSelectedItem();
    }

    public void setSelectedFilterFuelField(String selectedFilterFuelField) {
        this.filterFuelModel.get().setSelectedItem(selectedFilterFuelField);
    }

    public DefaultComboBoxModel<String> getFilterColorModel() {
        return filterColorModel.get();
    }

    public void setFilterColorModel(DefaultComboBoxModel<String> filterColorModel) {
        this.filterColorModel.set(filterColorModel);
    }

    public String getSelectedFilterColorField() {
        return (String) filterColorModel.get().getSelectedItem();
    }

    public void setSelectedFilterColorField(String selectedFilterColorField) {
        this.filterColorModel.get().setSelectedItem(selectedFilterColorField);
    }

    public DefaultComboBoxModel<String> getSearchModel() {
        return searchModel.get();
    }

    public void setSearchModel(DefaultComboBoxModel<String> searchModel) {
        this.searchModel.set(searchModel);
    }

    public String getSelectedSearchField() {
        return (String) searchModel.get().getSelectedItem();
    }

    public void setSelectedSearchField(String selectedSearchField) {
        this.searchModel.get().setSelectedItem(selectedSearchField);
    }

    public DefaultComboBoxModel<String> getSaveModel() {
        return saveModel.get();
    }

    public void setSaveModel(DefaultComboBoxModel<String> saveModel) {
        this.saveModel.set(saveModel);
    }

    public String getSelectedSaveField() {
        return (String) saveModel.get().getSelectedItem();
    }

    public void setSelectedSaveField(String selectedSaveField) {
        this.saveModel.get().setSelectedItem(selectedSaveField);
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

    public CommandInterface getLogOutCommand() {
        return logOutCommand;
    }

    public CommandInterface getSaveCommand() {
        return saveCommand;
    }

    public CommandInterface getServiceOperationsCommand() {
        return serviceOperationsCommand;
    }

    public CommandInterface getCarOperationsCommand() {
        return carOperationsCommand;
    }

    public CommandInterface getOwnerOperationsCommand() {
        return ownerOperationsCommand;
    }

    public CommandInterface getSearchCarsCommand() {
        return searchCarsCommand;
    }

    public CommandInterface getFilteringOptionsCommand() {
        return filteringOptionsCommand;
    }

    public CommandInterface getSortOptionsCommand() {
        return sortOptionsCommand;
    }
}
