package car_domain.view;

import client.Client;
import car_domain.controller.CarPopUpController;
import car_domain.model.Car;
import client.ProxyClient;
import user_domain.view.UserInterface;

import javax.swing.*;
import java.awt.*;

public class CarPopUpView extends JFrame {
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel brandLabel;
    private JLabel modelNameLabel;
    private JLabel colorLabel;
    private JLabel fuelTypeLabel;
    private JTextField idTextField;
    private JTextField brandTextField;
    private JTextField modelNameTextField;
    private JTextField colorTextField;
    private JTextField fuelTypeTextField;
    private JButton submitButton;
    private final Car selectedCar;
    CarPopUpController carPopUpController;


    public CarPopUpView(String name, Car selectedCar, OperationsView<Car> carOperationsView, ProxyClient client,
                        UserInterface userInterface) {
        super(name);
        this.selectedCar = selectedCar;
        this.carPopUpController = CarPopUpController.getControllerInstance(carOperationsView, this, client,userInterface);
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
        this.idLabel = new JLabel(carPopUpController.getLanguage().getFields().getCarFields().getId());
        this.idLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.idLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.idTextField = new JTextField();
        this.idTextField.setColumns(10);
        this.popUpPanel.add(this.idTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        this.brandLabel = new JLabel(carPopUpController.getLanguage().getFields().getCarFields().getBrand());
        this.brandLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.brandLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.brandTextField = new JTextField();
        this.brandTextField.setColumns(10);
        this.popUpPanel.add(this.brandTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        this.modelNameLabel = new JLabel(carPopUpController.getLanguage().getFields().getCarFields().getModelName());
        this.modelNameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.modelNameLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.modelNameTextField = new JTextField();
        this.modelNameTextField.setColumns(10);
        this.popUpPanel.add(this.modelNameTextField, c);

        c.gridy = 4;
        c.gridx = 0;
        this.colorLabel = new JLabel(carPopUpController.getLanguage().getFields().getCarFields().getColor());
        this.colorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.colorLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.colorTextField = new JTextField();
        this.colorTextField.setColumns(10);
        this.popUpPanel.add(this.colorTextField, c);

        c.gridy = 5;
        c.gridx = 0;
        this.fuelTypeLabel = new JLabel(carPopUpController.getLanguage().getFields().getCarFields().getFuelType());
        this.fuelTypeLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.fuelTypeLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.fuelTypeTextField = new JTextField();
        this.fuelTypeTextField.setColumns(10);
        this.popUpPanel.add(this.fuelTypeTextField, c);


        c.gridy = 6;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(carPopUpController);
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public JTextField getBrandTextField() {
        return brandTextField;
    }

    public JTextField getModelNameTextField() {
        return modelNameTextField;
    }

    public JTextField getColorTextField() {
        return colorTextField;
    }

    public JTextField getFuelTypeTextField() {
        return fuelTypeTextField;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }
}
