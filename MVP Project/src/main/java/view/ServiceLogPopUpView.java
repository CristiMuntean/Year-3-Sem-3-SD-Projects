package view;

import model.ServiceLog;
import presenter.ServiceLogPopUpPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class ServiceLogPopUpView extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private List<JLabel> labelList;
    private List<JLabel> fieldLabels;
    private List<JTextField> fieldTextFields;
    private JButton submitButton;
    private final ServiceLog selectedServiceLog;
    private final OperationsView<ServiceLog> serviceLogOperationsView;
    private final UserInterface employeeInterface;

    public ServiceLogPopUpView(String name, List<String> fieldNames, ServiceLog selectedServiceLog, OperationsView<ServiceLog> serviceLogOperationsView, UserInterface employeeInterface) {
        super(name);
        this.selectedServiceLog = selectedServiceLog;
        this.serviceLogOperationsView = serviceLogOperationsView;
        this.employeeInterface = employeeInterface;
        this.prepareGui(name, fieldNames);
    }

    private void prepareGui(String name, List<String> fieldNames) {
        this.setSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.preparePopUpView(name, fieldNames);
        this.setContentPane(this.contentPane);
    }

    private void preparePopUpView(String name, List<String> fieldNames) {
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
        int i = 0;
        this.fieldLabels = new ArrayList<>();
        this.fieldTextFields = new ArrayList<>();
        for (String fieldName : fieldNames) {
            c.gridy++;
            c.gridx = 0;
            this.fieldLabels.add(new JLabel(fieldName));
            this.fieldLabels.get(i).setFont(new Font("Serif", Font.PLAIN, 20));
            this.popUpPanel.add(this.fieldLabels.get(i), c);
            c.gridx = 2;
            c.gridwidth = 3;
            this.fieldTextFields.add(new JTextField());
            this.fieldTextFields.get(i).setColumns(10);
            this.popUpPanel.add(this.fieldTextFields.get(i), c);
            i++;
        }
        c.gridy++;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(this);
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JTextField field : this.fieldTextFields)
            if (isNullOrEmpty(field.getText())) return;
        ServiceLog serviceLog = new ServiceLog(
                this.fieldTextFields.get(0).getText(),
                this.fieldTextFields.get(1).getText(),
                this.fieldTextFields.get(2).getText()
        );

        ServiceLogPopUpPresenter ownerPopUpPresenter = ServiceLogPopUpPresenter.getPresenterInterface(serviceLogOperationsView, employeeInterface);
        if (e.getActionCommand().contains("Add")) {
            ownerPopUpPresenter.insertLog(serviceLog);
        } else {
            ownerPopUpPresenter.updateLog(serviceLog, selectedServiceLog);
        }
        this.dispose();
    }


}
