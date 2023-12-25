package user_domain.controller;

import car_domain.model.Car;
import car_domain.model.CarJoinedOwner;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import car_domain.view.CarsPopUpView;
import car_domain.view.OperationsView;
import client.ProxyClient;
import user_domain.view.EmployeeView;
import user_domain.view.language.Language;
import user_domain.view.language.Languages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EmployeeController extends AbstractEmployeeController implements ActionListener {
    private final EmployeeView employeeView;
    private final Language language;

    public EmployeeController(ProxyClient client) {
        language = Language.getLanguageInstance();
        setProxyClient(client);
        employeeView = new EmployeeView(language.getFields().getEmployeeTitleLabel(), this, getCarsWithOwnersTable());
        employeeView.setVisible(true);
        employeeView.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SORT", "FILTER" -> {
                createFilteringOptions(employeeView.getFilterComboBoxes());
                createSortOptions(employeeView.getSortCheckBoxes());
                List<CarJoinedOwner> sortedList = getCarJoinedOwnersList();
                sortedList = sortList(sortedList);
                sortedList = filterList(sortedList);
                JTable sortedTable;
                if (sortedList.size() == 0)
                    sortedTable = new JTable(new DefaultTableModel(getTableHeader(), 1));
                else sortedTable = createCarWithOwnersTable(sortedList);
                String filterOptions = getFilterOptions();
                employeeView.refreshPanel(sortedTable, filterOptions);

            }
            case "OWNER_OPERATIONS" -> {
                OperationsView<Owner> operationsView = new OperationsView<>("Owner operations", Owner.class, employeeView, getProxyClient());
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "CAR_OPERATIONS" -> {
                OperationsView<Car> operationsView = new OperationsView<>("Car operations", Car.class, employeeView, getProxyClient());
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "SERVICE_OPERATIONS" -> {
                OperationsView<ServiceLog> serviceLogOperationsView = new OperationsView<>("Service operations", ServiceLog.class, employeeView, getProxyClient());
                serviceLogOperationsView.setVisible(true);
                serviceLogOperationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "SEARCH" -> {
                if (!employeeView.getSearchComboBox().getSelectedItem().equals(Language.getLanguageInstance().getFields().getFilterOwnerComboBox())) {
                    List<Car> cars = searchCarsByOwner((String) employeeView.getSearchComboBox().getSelectedItem());
                    JTable carsTable = getCarsTable(cars);
                    CarsPopUpView carsPopUpView = new CarsPopUpView("Searched cars", carsTable);
                    carsPopUpView.setVisible(true);
                }
            }
            case "SAVE" -> {
                String format = employeeView.getSaveComboBox().getSelectedItem().toString();
                List<Car> carList = getCarList();
                switch (format) {
                    case "csv" -> saveInfoInCsvFormat(format, carList);
                    case "json" -> saveInfoInJsonFormat(format, carList);
                    case "xml" -> saveInfoInXmlFormat(format, carList);
                    case "txt" -> saveInfoInTxtFormat(format, carList);
                }
            }
            case "LOG_OUT" -> {
                LoginController loginController = new LoginController(getProxyClient());
                employeeView.dispose();
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
