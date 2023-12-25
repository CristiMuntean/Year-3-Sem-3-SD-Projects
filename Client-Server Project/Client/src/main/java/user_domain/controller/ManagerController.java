package user_domain.controller;

import car_domain.controller.ChartController;
import car_domain.model.Car;
import car_domain.model.CarJoinedOwner;
import car_domain.view.CarsPopUpView;
import client.ProxyClient;
import user_domain.view.ManagerView;
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

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ManagerController extends AbstractEmployeeController implements ActionListener {
    private final Language language;
    private final ManagerView managerView;

    public ManagerController(ProxyClient client) {
        setSortOptions("None");
        setFilterOptions("None");
        setProxyClient(client);
        language = Language.getLanguageInstance();
        managerView = new ManagerView(language.getFields().getManagerTitleLabel(), this, getCarsWithOwnersTable());
        managerView.setVisible(true);
        managerView.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SORT", "FILTER" -> {
                createFilteringOptions(managerView.getFilterComboBoxes());
                createSortOptions(managerView.getSortCheckBoxes());
                List<CarJoinedOwner> sortedList = getCarJoinedOwnersList();
                sortedList = sortList(sortedList);
                sortedList = filterList(sortedList);
                JTable sortedTable;
                if (sortedList.size() == 0)
                    sortedTable = new JTable(new DefaultTableModel(getTableHeader(), 1));
                else sortedTable = createCarWithOwnersTable(sortedList);
                String filterOptions = getFilterOptions();
                managerView.refreshPanel(sortedTable, filterOptions);
            }
            case "SEARCH" -> {
                if (!managerView.getSearchComboBox().getSelectedItem().equals(Language.getLanguageInstance().getFields().getFilterOwnerComboBox())) {
                    List<Car> cars = searchCarsByOwner((String) managerView.getSearchComboBox().getSelectedItem());
                    JTable carsTable = getCarsTable(cars);
                    CarsPopUpView carsPopUpView = new CarsPopUpView("Searched cars", carsTable);
                    carsPopUpView.setVisible(true);
                }
            }
            case "SAVE" -> {
                String format = managerView.getSaveComboBox().getSelectedItem().toString();
                List<Car> carList = getCarList();
                switch (format) {
                    case "csv" -> saveInfoInCsvFormat(format, carList);
                    case "json" -> saveInfoInJsonFormat(format, carList);
                    case "xml" -> saveInfoInXmlFormat(format, carList);
                    case "txt" -> saveInfoInTxtFormat(format, carList);
                }
            }
            case "STATISTICS" -> {
                ChartController chartController = new ChartController(getProxyClient(), managerView);
            }
            case "LOG_OUT" -> {
                LoginController loginController = new LoginController(getProxyClient());
                managerView.dispose();
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
