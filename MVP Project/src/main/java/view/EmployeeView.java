package view;

import model.Car;
import model.CarJoinedOwner;
import model.Owner;
import model.ServiceLog;
import presenter.EmployeePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeView extends JFrame implements ActionListener, EmployeeInterface {
    private JPanel contentPane;
    private JPanel emplyeePanel;
    private JLabel titleLabel;

    private JTable carsTable;
    private JLabel sortLabel;
    private List<JCheckBox> sortCheckBoxes;
    private JLabel filterLabel;
    private List<JComboBox<String>> filterComboBoxes;
    private JButton ownerOperationsButton;
    private JButton carOperationsButton;
    private JButton serviceLogButton;
    private JButton logOutButton;
    private final EmployeePresenter employeePresenter = EmployeePresenter.getPresenterInstance(this);

    public EmployeeView(String name) {
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.sortCheckBoxes = new ArrayList<>();
        this.sortCheckBoxes.add(new JCheckBox("Brand"));
        this.sortCheckBoxes.add(new JCheckBox("Fuel Type"));

        this.sortCheckBoxes.get(0).setSelected(false);
        this.sortCheckBoxes.get(0).addActionListener(this);
        this.sortCheckBoxes.get(0).setActionCommand("SORT");
        this.sortCheckBoxes.get(1).setSelected(false);
        this.sortCheckBoxes.get(1).addActionListener(this);
        this.sortCheckBoxes.get(1).setActionCommand("SORT");

        this.filterComboBoxes = new ArrayList<>(4);
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());

        this.filterComboBoxes.get(0).setSelectedItem("Owner");
        this.filterComboBoxes.get(1).setSelectedItem("Brand");
        this.filterComboBoxes.get(2).setSelectedItem("Fuel Type");
        this.filterComboBoxes.get(3).setSelectedItem("Color");

        this.prepareEmployeePanel();
        this.setContentPane(this.contentPane);
    }

    private void reprepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareEmployeePanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareEmployeePanel() {
        this.emplyeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 0;
        this.titleLabel = new JLabel("Employee window");
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.emplyeePanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        List<CarJoinedOwner> carsWithOwners = employeePresenter.getAllCarsWithOwners();

        carsWithOwners = employeePresenter.sortList(carsWithOwners);
        carsWithOwners = employeePresenter.filterList(carsWithOwners);

        this.carsTable = employeePresenter.getCarsWithOwnersTable(carsWithOwners);
        JScrollPane scrollPane = new JScrollPane(this.carsTable);
        this.carsTable.setFillsViewportHeight(true);
        c.gridwidth = 6;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        this.emplyeePanel.add(scrollPane, c);

        this.sortLabel = new JLabel("Sort car table by:");
        this.sortLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        this.emplyeePanel.add(this.sortLabel, c);

        c.gridx = 3;
        c.gridwidth = 1;
        for (JCheckBox checkBox : this.sortCheckBoxes) {
            this.emplyeePanel.add(checkBox, c);
            c.gridx++;
        }

        List<String> distinctOwners = employeePresenter.getDistinctOwners();
        distinctOwners.add(0, "Owner");
        this.filterComboBoxes.set(0, new JComboBox<>(distinctOwners.toArray(new String[distinctOwners.size()])));

        List<String> distinctBrands = employeePresenter.getDistinctCarBrands();
        distinctBrands.add(0, "Car Brand");
        this.filterComboBoxes.set(1, new JComboBox<>(distinctBrands.toArray(new String[distinctBrands.size()])));

        List<String> distinctFuelTypes = employeePresenter.getDistinctFuelTypes();
        distinctFuelTypes.add(0, "Fuel Type");
        this.filterComboBoxes.set(2, new JComboBox<>(distinctFuelTypes.toArray(new String[distinctFuelTypes.size()])));

        List<String> distinctColors = employeePresenter.getDistinctColors();
        distinctColors.add(0, "Color");
        this.filterComboBoxes.set(3, new JComboBox<>(distinctColors.toArray(new String[distinctColors.size()])));

        Arrays.stream(employeePresenter.getFilterOptions().split(" ")).forEach(element -> {
            if (element.contains("Owner:"))
                filterComboBoxes.get(0).setSelectedItem(element.split(":")[1].replace("_", " "));
            if (element.contains("CarBrand:")) filterComboBoxes.get(1).setSelectedItem(element.split(":")[1]);
            if (element.contains("FuelType:")) filterComboBoxes.get(2).setSelectedItem(element.split(":")[1]);
            if (element.contains("Color:")) filterComboBoxes.get(3).setSelectedItem(element.split(":")[1]);
        });

        filterComboBoxes.forEach(element -> {
            element.addActionListener(this);
            element.setActionCommand("FILTER");
        });

        this.filterLabel = new JLabel("Select filters to apply on the table above: ");
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 4;
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

        c.gridy = 5;
        c.gridwidth = 3;
        c.gridx = 0;
        this.ownerOperationsButton = new JButton("Owner operations");
        this.ownerOperationsButton.addActionListener(this);
        this.ownerOperationsButton.setActionCommand("OWNER_OPERATIONS");
        this.emplyeePanel.add(this.ownerOperationsButton, c);

        c.gridx = 3;
        this.carOperationsButton = new JButton("Car operations");
        this.carOperationsButton.addActionListener(this);
        this.carOperationsButton.setActionCommand("CAR_OPERATIONS");
        this.emplyeePanel.add(this.carOperationsButton, c);

        c.gridx = 0;
        c.gridy = 6;
        this.serviceLogButton = new JButton("Service operations");
        this.serviceLogButton.addActionListener(this);
        this.serviceLogButton.setActionCommand("SERVICE_OPERATIONS");
        this.emplyeePanel.add(this.serviceLogButton, c);


        c.gridx = 3;
        this.logOutButton = new JButton("Log out");
        this.logOutButton.addActionListener(this);
        this.logOutButton.setActionCommand("LOG_OUT");
        this.emplyeePanel.add(this.logOutButton, c);

        this.contentPane.add(this.emplyeePanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SORT" -> {
                employeePresenter.createSortOptions();
                this.clearPanel();
                this.reprepareGui();
            }
            case "FILTER" -> {
                employeePresenter.createFilteringOptions(filterComboBoxes);
                this.clearPanel();
                this.reprepareGui();
            }
            case "OWNER_OPERATIONS" -> {
                OperationsView<Owner> operationsView = new OperationsView<>("Owner operations", Owner.class, this);
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "CAR_OPERATIONS" -> {
                OperationsView<Car> operationsView = new OperationsView<>("Car operations", Car.class, this);
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "SERVICE_OPERATIONS" -> {
                OperationsView<ServiceLog> serviceLogOperationsView = new OperationsView<>("Service operations", ServiceLog.class, this);
                serviceLogOperationsView.setVisible(true);
                serviceLogOperationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "LOG_OUT" -> {
                this.dispose();
                LoginView loginView = new LoginView("Log in");
                loginView.setVisible(true);
                loginView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        }

    }

    private void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    @Override
    public void refreshPanel() {
        this.clearPanel();
        this.prepareGui();
    }

    @Override
    public List<JCheckBox> getSortCheckBoxes() {
        return sortCheckBoxes;
    }
}

