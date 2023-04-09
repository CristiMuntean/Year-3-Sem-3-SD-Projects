package view;

import model.Car;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.CarPopUpVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarPopUpView extends JFrame {
    private static final CarPopUpVM carPopUpVM = CarPopUpVM.getVMInstance();
    private final OperationsView<Car> carOperationsView;
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel brandLabel;
    private JLabel modelLabel;
    private JLabel colorLabel;
    private JLabel fuelLabel;
    @Bind(value = "text", target = "idTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField idTextField;
    @Bind(value = "text", target = "brandTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField brandTextField;
    @Bind(value = "text", target = "modelTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField modelTextField;
    @Bind(value = "text", target = "colorTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField colorTextField;
    @Bind(value = "text", target = "fuelTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField fuelTextField;
    private JButton submitButton;

    public CarPopUpView(String name, Car selectedCar, OperationsView<Car> carOperationsView) {
        super(name);
        carPopUpVM.setSelectedCar(selectedCar);
        this.carOperationsView = carOperationsView;
        this.prepareGui(name);
        try {
            Binder.bind(this, carPopUpVM);
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
        this.idLabel = new JLabel("id");
        this.idLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.idLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.idTextField = new JTextField();
        this.idTextField.setColumns(10);
        this.popUpPanel.add(this.idTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        this.brandLabel = new JLabel("brand");
        this.brandLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.brandLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.brandTextField = new JTextField();
        this.brandTextField.setColumns(10);
        this.popUpPanel.add(this.brandTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        this.modelLabel = new JLabel("model");
        this.modelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.modelLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.modelTextField = new JTextField();
        this.modelTextField.setColumns(10);
        this.popUpPanel.add(this.modelTextField, c);

        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 1;
        this.colorLabel = new JLabel("color");
        this.colorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.colorLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.colorTextField = new JTextField();
        this.colorTextField.setColumns(10);
        this.popUpPanel.add(this.colorTextField, c);

        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 1;
        this.fuelLabel = new JLabel("fuel");
        this.fuelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.fuelLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.fuelTextField = new JTextField();
        this.fuelTextField.setColumns(10);
        this.popUpPanel.add(this.fuelTextField, c);

        c.gridy++;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().contains("Add")) {
                    carPopUpVM.getCarCommandInsert().execute();
                } else {
                    carPopUpVM.getCarCommandUpdate().execute();
                }
                disposeWindow();
                carOperationsView.refreshPanel();
            }
        });
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public void disposeWindow() {
        this.dispose();
    }
}
