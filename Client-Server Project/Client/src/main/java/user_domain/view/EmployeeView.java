package user_domain.view;

import user_domain.controller.EmployeeController;
import user_domain.view.language.Languages;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class EmployeeView extends JFrame implements EmployeeInterface, Observer {
    private final EmployeeController employeeController;
    private JPanel contentPane;
    private JPanel emplyeePanel;
    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
    private JLabel titleLabel;
    private JTable carsTable;
    private JLabel sortLabel;
    private List<JCheckBox> sortCheckBoxes;
    private JLabel filterLabel;
    private List<JComboBox<String>> filterComboBoxes;
    private JLabel searchLabel;
    private JComboBox<String> searchComboBox;
    private JButton ownerOperationsButton;
    private JButton carOperationsButton;
    private JButton serviceLogButton;
    private JComboBox<String> saveComboBox;
    private JButton saveButton;
    private JButton logOutButton;

    public EmployeeView(String name, EmployeeController employeeController, JTable carsWithOwnersTable) {
        super(name);
        this.employeeController = employeeController;
        employeeController.getLanguage().addObserver(this);
        this.prepareGui(carsWithOwnersTable);
    }

    private void prepareGui(JTable carsWithOwnersTable) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.sortCheckBoxes = new ArrayList<>();
        this.sortCheckBoxes.add(new JCheckBox(employeeController.getLanguage().getFields().getSortBrandCheckBox()));
        this.sortCheckBoxes.add(new JCheckBox(employeeController.getLanguage().getFields().getSortFuelTypeCheckBox()));

        this.sortCheckBoxes.get(0).setSelected(false);
        this.sortCheckBoxes.get(0).addActionListener(employeeController);
        this.sortCheckBoxes.get(0).setActionCommand("SORT");
        this.sortCheckBoxes.get(1).setSelected(false);
        this.sortCheckBoxes.get(1).addActionListener(employeeController);
        this.sortCheckBoxes.get(1).setActionCommand("SORT");

        this.filterComboBoxes = new ArrayList<>(4);
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());

        this.filterComboBoxes.get(0).setSelectedItem(employeeController.getLanguage().getFields().getFilterOwnerComboBox());
        this.filterComboBoxes.get(1).setSelectedItem(employeeController.getLanguage().getFields().getFilterBrandComboBox());
        this.filterComboBoxes.get(2).setSelectedItem(employeeController.getLanguage().getFields().getFilterFuelTypeComboBox());
        this.filterComboBoxes.get(3).setSelectedItem(employeeController.getLanguage().getFields().getFilterColorComboBox());

        this.prepareEmployeePanel(carsWithOwnersTable, "None");
        this.setContentPane(this.contentPane);
    }

    @Override
    public void reprepareGui(JTable carsWithOwnersTable, String filterOptions) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareEmployeePanel(carsWithOwnersTable, filterOptions);
        this.setContentPane(this.contentPane);
    }

    private void prepareEmployeePanel(JTable carsWithOwnersTable, String filterOptions) {
        this.emplyeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.fill = GridBagConstraints.HORIZONTAL;
        this.changeLanguageEnglishButton = new JButton(employeeController.getLanguage().getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageEnglishButton.addActionListener(employeeController);
        this.changeLanguageEnglishButton.setActionCommand("ENGLISH");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!employeeController.getLanguage().getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.emplyeePanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(employeeController.getLanguage().getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageRomanianButton.addActionListener(employeeController);
        this.changeLanguageRomanianButton.setActionCommand("ROMANIAN");

        if(!employeeController.getLanguage().getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.emplyeePanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(employeeController.getLanguage().getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageItalianButton.addActionListener(employeeController);
        this.changeLanguageItalianButton.setActionCommand("ITALIAN");

        if(!employeeController.getLanguage().getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.emplyeePanel.add(this.changeLanguageItalianButton,c);
        }


        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        this.titleLabel = new JLabel(employeeController.getLanguage().getFields().getEmployeeTitleLabel());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.emplyeePanel.add(this.titleLabel, c);


        c.fill = GridBagConstraints.HORIZONTAL;

        this.carsTable = carsWithOwnersTable;
        JScrollPane scrollPane = new JScrollPane(this.carsTable);
        this.carsTable.setFillsViewportHeight(true);
        c.gridwidth = 6;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 2;
        this.emplyeePanel.add(scrollPane, c);

        this.sortLabel = new JLabel(employeeController.getLanguage().getFields().getSortLabel());
        this.sortLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        this.emplyeePanel.add(this.sortLabel, c);

        c.gridx = 3;
        c.gridwidth = 1;
        for (JCheckBox checkBox : this.sortCheckBoxes) {
            this.emplyeePanel.add(checkBox, c);
            c.gridx++;
        }

        List<String> distinctOwners = employeeController.getDistinctOwners();
        distinctOwners.add(0, employeeController.getLanguage().getFields().getFilterOwnerComboBox());
        this.filterComboBoxes.set(0, new JComboBox<>(distinctOwners.toArray(new String[distinctOwners.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("Owner:")) {
                this.filterComboBoxes.get(0).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        List<String> distinctBrands = employeeController.getDistinctCarBrands();
        distinctBrands.add(0, employeeController.getLanguage().getFields().getFilterBrandComboBox());
        this.filterComboBoxes.set(1, new JComboBox<>(distinctBrands.toArray(new String[distinctBrands.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("CarBrand:")) {
                this.filterComboBoxes.get(1).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }


        List<String> distinctFuelTypes = employeeController.getDistinctFuelTypes();
        distinctFuelTypes.add(0, employeeController.getLanguage().getFields().getFilterFuelTypeComboBox());
        this.filterComboBoxes.set(2, new JComboBox<>(distinctFuelTypes.toArray(new String[distinctFuelTypes.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("FuelType:")) {
                this.filterComboBoxes.get(2).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        List<String> distinctColors = employeeController.getDistinctColors();
        distinctColors.add(0, employeeController.getLanguage().getFields().getFilterColorComboBox());
        this.filterComboBoxes.set(3, new JComboBox<>(distinctColors.toArray(new String[distinctColors.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("Color:")) {
                this.filterComboBoxes.get(3).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        filterComboBoxes.forEach(element -> {
            element.addActionListener(employeeController);
            element.setActionCommand("FILTER");
        });

        this.filterLabel = new JLabel(employeeController.getLanguage().getFields().getFilterLabel());
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 5;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        this.emplyeePanel.add(this.filterLabel, c);

        c.gridwidth = 1;
        c.gridx = 2;
        filterComboBoxes.forEach(element -> {
            this.emplyeePanel.add(element, c);
            c.gridx++;
        });

        this.searchLabel = new JLabel(employeeController.getLanguage().getFields().getSearchLabel());
        this.searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridx = 0;
        this.emplyeePanel.add(this.searchLabel, c);
        c.gridwidth = 3;
        c.gridx = 2;

        this.searchComboBox = new JComboBox<>(distinctOwners.toArray(new String[distinctOwners.size()]));
        this.searchComboBox.addActionListener(employeeController);
        this.searchComboBox.setActionCommand("SEARCH");
        this.emplyeePanel.add(this.searchComboBox, c);

        c.gridy = 7;
        c.gridwidth = 3;
        c.gridx = 0;
        this.ownerOperationsButton = new JButton(employeeController.getLanguage().getFields().getOwnerOperationsButton());
        this.ownerOperationsButton.addActionListener(employeeController);
        this.ownerOperationsButton.setActionCommand("OWNER_OPERATIONS");
        this.emplyeePanel.add(this.ownerOperationsButton, c);

        c.gridx = 3;
        this.carOperationsButton = new JButton(employeeController.getLanguage().getFields().getCarOperationsButton());
        this.carOperationsButton.addActionListener(employeeController);
        this.carOperationsButton.setActionCommand("CAR_OPERATIONS");
        this.emplyeePanel.add(this.carOperationsButton, c);

        c.gridx = 0;
        c.gridy = 8;
        this.serviceLogButton = new JButton(employeeController.getLanguage().getFields().getServiceOperationsButton());
        this.serviceLogButton.addActionListener(employeeController);
        this.serviceLogButton.setActionCommand("SERVICE_OPERATIONS");
        this.emplyeePanel.add(this.serviceLogButton, c);

        c.gridy = 9;
        this.saveButton = new JButton(employeeController.getLanguage().getFields().getSaveButton());
        this.saveButton.addActionListener(employeeController);
        this.saveButton.setActionCommand("SAVE");
        this.emplyeePanel.add(this.saveButton, c);

        c.gridx = 3;
        this.saveComboBox = new JComboBox<>(new String[]{"csv", "json", "xml", "txt"});
        this.emplyeePanel.add(this.saveComboBox, c);

        c.gridy = 10;
        this.logOutButton = new JButton(employeeController.getLanguage().getFields().getLogOutButton());
        this.logOutButton.addActionListener(employeeController);
        this.logOutButton.setActionCommand("LOG_OUT");
        this.emplyeePanel.add(this.logOutButton, c);

        this.contentPane.add(this.emplyeePanel);
    }

    @Override
    public void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    @Override
    public void refreshPanel(JTable carsWithOwnersTable, String filterOptions) {
        this.clearPanel();
        this.reprepareGui(carsWithOwnersTable, filterOptions);
    }


    @Override
    public void disposeWindow() {
        this.dispose();
    }

    @Override
    public void refreshPanel() {
        this.clearPanel();
        JTable carsTableRefreshed = employeeController.getCarsWithOwnersTable();
        this.reprepareGui(carsTableRefreshed,"None");
    }

    @Override
    public List<JCheckBox> getSortCheckBoxes() {
        return sortCheckBoxes;
    }

    public List<JComboBox<String>> getFilterComboBoxes() {
        return filterComboBoxes;
    }

    @Override
    public JComboBox<String> getSearchComboBox() {
        return searchComboBox;
    }

    @Override
    public JComboBox<String> getSaveComboBox() {
        return saveComboBox;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel(carsTable,"None");
    }
}

