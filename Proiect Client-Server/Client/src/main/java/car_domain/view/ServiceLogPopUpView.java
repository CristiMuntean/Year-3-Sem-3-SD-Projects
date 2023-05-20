package car_domain.view;

import client.Client;
import car_domain.controller.ServiceLogPopUpController;
import car_domain.model.ServiceLog;
import client.ProxyClient;
import user_domain.view.UserInterface;

import javax.swing.*;
import java.awt.*;

public class ServiceLogPopUpView extends JFrame {

    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel serviceNumberLabel;
    private JLabel ownerCnpLabel;
    private JLabel carIdLabel;
    private JTextField serviceNumberTextField;
    private JTextField ownerCnpTextField;
    private JTextField carIdTextField;
    private JButton submitButton;
    private final ServiceLog selectedServiceLog;
    ServiceLogPopUpController serviceLogPopUpController;


    public ServiceLogPopUpView(String name, ServiceLog selectedServiceLog, OperationsView<ServiceLog> serviceLogOperationsView, UserInterface employeeInterface, ProxyClient client) {
        super(name);
        this.selectedServiceLog = selectedServiceLog;
        this.serviceLogPopUpController = ServiceLogPopUpController.getControllerInterface(serviceLogOperationsView, employeeInterface, this, client);
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.preparePopUpView(name);
        this.setContentPane(this.contentPane);
    }

    private void preparePopUpView(String name) {
        this.popUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        this.popUpPanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;

        c.gridy = 1;
        c.gridx = 0;
        this.serviceNumberLabel = new JLabel(serviceLogPopUpController.getLanguage().getFields().getServiceLogFields().getServiceNumber());
        this.serviceNumberLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.serviceNumberLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.serviceNumberTextField = new JTextField();
        this.serviceNumberTextField.setColumns(10);
        this.popUpPanel.add(this.serviceNumberTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        this.ownerCnpLabel = new JLabel(serviceLogPopUpController.getLanguage().getFields().getServiceLogFields().getOwnerCnp());
        this.ownerCnpLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.ownerCnpLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.ownerCnpTextField = new JTextField();
        this.ownerCnpTextField.setColumns(10);
        this.popUpPanel.add(this.ownerCnpTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        this.carIdLabel = new JLabel(serviceLogPopUpController.getLanguage().getFields().getServiceLogFields().getCarId());
        this.carIdLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.carIdLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.carIdTextField = new JTextField();
        this.carIdTextField.setColumns(10);
        this.popUpPanel.add(this.carIdTextField, c);

        c.gridy = 4;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(serviceLogPopUpController);
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public ServiceLog getSelectedServiceLog() {
        return selectedServiceLog;
    }

    public JTextField getServiceNumberTextField() {
        return serviceNumberTextField;
    }

    public JTextField getOwnerCnpTextField() {
        return ownerCnpTextField;
    }

    public JTextField getCarIdTextField() {
        return carIdTextField;
    }
}
