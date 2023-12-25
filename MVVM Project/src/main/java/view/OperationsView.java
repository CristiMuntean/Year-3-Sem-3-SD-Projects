package view;

import model.Car;
import model.Owner;
import model.ServiceLog;
import model.User;
import viewModel.OperationsVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationsView<T> extends JFrame {
    private final Class<T> operationClass;
    private final OperationsVM operationsVM;
    private JPanel contentPane;
    private JPanel operationsPanel;
    private JLabel titleLabel;
    private JTable operationsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public OperationsView(String name, Class operationClass) {
        super(name);
        this.operationClass = operationClass;
        operationsVM = new OperationsVM<T>(operationClass);
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

        this.titleLabel = new JLabel(operationsVM.prepareTitle());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.operationsPanel.add(this.titleLabel, c);

        this.operationsTable = operationsVM.getTable();
        JScrollPane scrollPane = new JScrollPane(this.operationsTable);
        this.operationsTable.setFillsViewportHeight(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridheight = 3;
        c.gridy = 1;
        this.operationsPanel.add(scrollPane, c);

        this.addButton = new JButton("Add a new " + operationClass.getSimpleName());
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operationClass.getSimpleName().equals("Owner")) {
                    operationsVM.createOwnerPopUpView("Add a new owner", null, getInstance());
                } else if (operationClass.getSimpleName().equals("Car")) {
                    operationsVM.createCarPopUpView("Add a new car", null, getInstance());
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    operationsVM.createServiceLogPopUpView("Add a new service log",
                            null, getInstance());
                } else if (operationClass.getSimpleName().equals("User")) {
                    operationsVM.createUserPopUpView("Add a new user", null, getInstance());
                }
            }
        });
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 5;
        this.operationsPanel.add(this.addButton, c);

        this.updateButton = new JButton("Update selected " + operationClass.getSimpleName());
        this.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = operationsTable.getSelectedRow();
                if (operationClass.getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2));
                    operationsVM.createOwnerPopUpView("Update the selected owner", owner, getInstance());
                } else if (operationClass.getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3),
                            (String) operationsTable.getValueAt(row, 4));
                    operationsVM.createCarPopUpView("Update the selected car", car, getInstance());
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = operationsVM.getServiceLogFromServiceLogNumber((String) operationsTable.getValueAt(row, 0));
                    operationsVM.createServiceLogPopUpView("Update the selected service log", serviceLog, getInstance());
                } else if (operationClass.getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3)
                    );
                    operationsVM.createUserPopUpView("Update the selected user", user, getInstance());
                }
            }
        });
        c.gridy = 2;
        this.operationsPanel.add(this.updateButton, c);

        this.deleteButton = new JButton("Delete selected " + operationClass.getSimpleName());
        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = operationsTable.getSelectedRow();
                if (operationClass.getSimpleName().equals("Owner")) {
                    Owner owner = new Owner(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2));
                    operationsVM.deleteOwner(owner);
                } else if (operationClass.getSimpleName().equals("Car")) {
                    Car car = new Car(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3),
                            (String) operationsTable.getValueAt(row, 4));
                    operationsVM.deleteCar(car);
                } else if (operationClass.getSimpleName().equals("ServiceLog")) {
                    ServiceLog serviceLog = operationsVM.getServiceLogFromServiceLogNumber((String) operationsTable.getValueAt(row, 0));
                    operationsVM.deleteServiceLog(serviceLog);
                } else if (operationClass.getSimpleName().equals("User")) {
                    User user = new User(
                            (String) operationsTable.getValueAt(row, 0),
                            (String) operationsTable.getValueAt(row, 1),
                            (String) operationsTable.getValueAt(row, 2),
                            (String) operationsTable.getValueAt(row, 3)
                    );
                    operationsVM.deleteUser(user);
                }
                refreshPanel();
            }
        });
        c.gridy = 3;
        this.operationsPanel.add(this.deleteButton, c);

        this.contentPane.add(this.operationsPanel);
    }

    public OperationsView<T> getInstance() {
        return this;
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
