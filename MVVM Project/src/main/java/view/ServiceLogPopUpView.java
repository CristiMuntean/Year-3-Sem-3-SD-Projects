package view;

import model.ServiceLog;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.ServiceLogPopUpVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServiceLogPopUpView extends JFrame {

    private static final ServiceLogPopUpVM serviceLogPopUpVM = ServiceLogPopUpVM.getVMInterface();
    private final OperationsView<ServiceLog> serviceLogOperationsView;
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel serviceNumberLabel;
    private JLabel cnpLabel;
    private JLabel carIdLabel;
    @Bind(value = "text", target = "serviceNumberTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField serviceNumberTextField;
    @Bind(value = "text", target = "cnpTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField cnpTextField;
    @Bind(value = "text", target = "carIdTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField carIdTextField;
    private JButton submitButton;

    public ServiceLogPopUpView(String name, ServiceLog selectedServiceLog, OperationsView<ServiceLog> serviceLogOperationsView) {
        super(name);
        serviceLogPopUpVM.setSelectedLog(selectedServiceLog);
        this.serviceLogOperationsView = serviceLogOperationsView;
        this.prepareGui(name);

        try {
            Binder.bind(this, serviceLogPopUpVM);
        } catch (BindingException e) {
            throw new RuntimeException(e);
        }
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

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        this.serviceNumberLabel = new JLabel("service number");
        this.serviceNumberLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.serviceNumberLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.serviceNumberTextField = new JTextField();
        this.serviceNumberTextField.setColumns(10);
        this.popUpPanel.add(this.serviceNumberTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        this.cnpLabel = new JLabel("owner cnp");
        this.cnpLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.cnpLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.cnpTextField = new JTextField();
        this.cnpTextField.setColumns(10);
        this.popUpPanel.add(this.cnpTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        this.carIdLabel = new JLabel("car id");
        this.carIdLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.carIdLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.carIdTextField = new JTextField();
        this.carIdTextField.setColumns(10);
        this.popUpPanel.add(this.carIdTextField, c);


        c.gridy = 4;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().contains("Add")) {
                    serviceLogPopUpVM.getServiceLogCommandInsert().execute();
                } else {
                    serviceLogPopUpVM.getServiceLogCommandUpdate().execute();
                }
                disposeWindow();
                getServiceLogOperationsView().refreshPanel();
            }
        });
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public void disposeWindow() {
        this.dispose();
    }

    public OperationsView<ServiceLog> getServiceLogOperationsView() {
        return serviceLogOperationsView;
    }
}
