package view;

import model.Car;
import model.Owner;
import model.ServiceLog;
import model.User;
import presenter.OperationsPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Arrays;

public class OperationsView<T> extends JFrame implements ActionListener, OperationsInterface<T> {
    private JPanel contentPane;
    private JPanel operationsPanel;
    private final Class<T> operationClass;
    private JLabel titleLabel;
    private JTable operationsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private final OperationsPresenter operationsPresenter;
    private final UserInterface userInterface;

    public OperationsView(String name, Class operationClass, UserInterface userInterface) {
        super(name);
        this.operationClass = operationClass;
        operationsPresenter = new OperationsPresenter<T>(operationClass);
        this.userInterface = userInterface;
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.prepareOperationsPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareOperationsPanel() {
        this.operationsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 0;

        this.titleLabel = new JLabel(operationsPresenter.prepareTitle());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.operationsPanel.add(this.titleLabel, c);

        this.operationsTable = operationsPresenter.getTable();
        JScrollPane scrollPane = new JScrollPane(this.operationsTable);
        this.operationsTable.setFillsViewportHeight(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridheight = 3;
        c.gridy = 1;
        this.operationsPanel.add(scrollPane, c);

        this.addButton = new JButton("Add a new " + operationClass.getSimpleName());
        this.addButton.addActionListener(this);
        this.addButton.setActionCommand("ADD");
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 5;
        this.operationsPanel.add(this.addButton, c);

        this.updateButton = new JButton("Update selected " + operationClass.getSimpleName());
        this.updateButton.addActionListener(this);
        this.updateButton.setActionCommand("UPDATE");
        c.gridy = 2;
        this.operationsPanel.add(this.updateButton, c);

        this.deleteButton = new JButton("Delete selected " + operationClass.getSimpleName());
        this.deleteButton.addActionListener(this);
        this.deleteButton.setActionCommand("DELETE");
        c.gridy = 3;
        this.operationsPanel.add(this.deleteButton, c);

        this.contentPane.add(this.operationsPanel);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "ADD" -> {
                if (operationClass.getSimpleName().equals("Owner")) {
                    operationsPresenter.createOwnerPopUpView("Add a new owner",
                            Arrays.stream(Owner.class.getDeclaredFields()).map(Field::getName).toList(),
                            null, this);
                } else if (operationClass.getSimpleName().equals("Car")) {
                    operationsPresenter.createCarPopUpView("Add a new car",
                            Arrays.stream(Car.class.getDeclaredFields()).map(Field::getName).toList(),
                            null, this);
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    operationsPresenter.createServiceLogPopUpView("Add a new service log",
                            Arrays.stream(ServiceLog.class.getDeclaredFields()).map(Field::getName).toList(),
                            null, this, this.userInterface);
                } else if (operationClass.getSimpleName().equals("User")) {
                    operationsPresenter.createUserPopUpView("Add a new user",
                            Arrays.stream(User.class.getDeclaredFields()).map(Field::getName).toList(),
                            null, this, this.userInterface);
                }
            }
            case "UPDATE" -> {
                int row = operationsTable.getSelectedRow();
                if (operationClass.getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2));
                    operationsPresenter.createOwnerPopUpView("Update the selected owner",
                            Arrays.stream(Owner.class.getDeclaredFields()).map(Field::getName).toList(),
                            owner, this);
                } else if (operationClass.getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3),
                            (String) operationsTable.getValueAt(row, 4));
                    operationsPresenter.createCarPopUpView("Update the selected car",
                            Arrays.stream(Car.class.getDeclaredFields()).map(Field::getName).toList(),
                            car, this);
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = operationsPresenter.getServiceLogFromServiceLogNumber((String) operationsTable.getValueAt(row, 0));
                    operationsPresenter.createServiceLogPopUpView("Update the selected service log",
                            Arrays.stream(ServiceLog.class.getDeclaredFields()).map(Field::getName).toList(),
                            serviceLog, this, userInterface);
                } else if (operationClass.getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3)
                    );
                    operationsPresenter.createUserPopUpView("Update the selected user",
                            Arrays.stream(User.class.getDeclaredFields()).map(Field::getName).toList(),
                            user, this, userInterface);
                }
            }
            case "DELETE" -> {
                int row = operationsTable.getSelectedRow();
                if (operationClass.getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2));
                    operationsPresenter.deleteOwner(owner);
                } else if (operationClass.getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3),
                            (String) operationsTable.getValueAt(row, 4));
                    operationsPresenter.deleteCar(car);
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = operationsPresenter.getServiceLogFromServiceLogNumber((String) operationsTable.getValueAt(row, 0));
                    operationsPresenter.deleteServiceLog(serviceLog);
                } else if (operationClass.getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3)
                    );
                    operationsPresenter.deleteUser(user);
                }
                this.refreshPanel();
                userInterface.refreshPanel();
            }
        }
    }

    private void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.operationsPanel.revalidate();
        this.contentPane.repaint();
    }

    public void refreshPanel() {
        this.clearPanel();
        this.prepareGui();
    }
}
