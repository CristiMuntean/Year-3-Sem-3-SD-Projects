package car_domain.view;

import client.Client;
import car_domain.controller.OwnerPopUpController;
import car_domain.model.Owner;
import client.ProxyClient;
import user_domain.view.UserInterface;

import javax.swing.*;
import java.awt.*;

public class OwnerPopUpView extends JFrame {

    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel cnpLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JTextField cnpTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JButton submitButton;
    private final Owner selectedOwner;
    private final OperationsView<Owner> ownerOperationsView;
    private OwnerPopUpController ownerPopUpController;

    public OwnerPopUpView(String name, Owner selectedOwner, OperationsView<Owner> ownerOperationsView, ProxyClient client, UserInterface userInterface) {
        super(name);
        this.selectedOwner = selectedOwner;
        ownerPopUpController =  OwnerPopUpController.getControllerInstance(ownerOperationsView, this, client, userInterface);
        this.ownerOperationsView = ownerOperationsView;
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
        this.cnpLabel = new JLabel(ownerPopUpController.getLanguage().getFields().getOwnerFields().getCnp());
        this.cnpLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.cnpLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.cnpTextField = new JTextField();
        this.cnpTextField.setColumns(10);
        this.popUpPanel.add(this.cnpTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        this.nameLabel = new JLabel(ownerPopUpController.getLanguage().getFields().getOwnerFields().getName());
        this.nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.nameLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.nameTextField = new JTextField();
        this.nameTextField.setColumns(10);
        this.popUpPanel.add(this.nameTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        this.surnameLabel = new JLabel(ownerPopUpController.getLanguage().getFields().getOwnerFields().getSurname());
        this.surnameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.surnameLabel, c);
        c.gridx = 2;
        c.gridwidth = 3;
        this.surnameTextField = new JTextField();
        this.surnameTextField.setColumns(10);
        this.popUpPanel.add(this.surnameTextField, c);


        c.gridy = 4;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(ownerPopUpController);
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public JTextField getCnpTextField() {
        return cnpTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getSurnameTextField() {
        return surnameTextField;
    }

    public Owner getSelectedOwner() {
        return selectedOwner;
    }
}
