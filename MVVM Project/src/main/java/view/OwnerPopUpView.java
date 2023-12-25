package view;

import model.Owner;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.OwnerPopUpVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OwnerPopUpView extends JFrame {

    private static final OwnerPopUpVM ownerPopUpVM = OwnerPopUpVM.getVMInstance();
    private final OperationsView<Owner> ownerOperationsView;
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel cnpLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    @Bind(value = "text", target = "cnpTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField cnpTextField;
    @Bind(value = "text", target = "nameTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField nameTextField;
    @Bind(value = "text", target = "surnameTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField surnameTextField;
    private JButton submitButton;

    public OwnerPopUpView(String name, Owner selectedOwner, OperationsView<Owner> ownerOperationsView) {
        super(name);
        ownerPopUpVM.setSelectedOwner(selectedOwner);
        this.ownerOperationsView = ownerOperationsView;
        this.prepareGui(name);

        try {
            Binder.bind(this, ownerPopUpVM);
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
        this.cnpLabel = new JLabel("cnp");
        this.cnpLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.cnpLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.cnpTextField = new JTextField();
        this.cnpTextField.setColumns(10);
        this.popUpPanel.add(this.cnpTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        this.nameLabel = new JLabel("name");
        this.nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.nameLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.nameTextField = new JTextField();
        this.nameTextField.setColumns(10);
        this.popUpPanel.add(this.nameTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        this.surnameLabel = new JLabel("surname");
        this.surnameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.surnameLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.surnameTextField = new JTextField();
        this.surnameTextField.setColumns(10);
        this.popUpPanel.add(this.surnameTextField, c);

        c.gridy = 4;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().contains("Add")) {
                    ownerPopUpVM.getOwnerCommandInsert().execute();
                } else {
                    ownerPopUpVM.getOwnerCommandUpdate().execute();
                }
                disposeWindow();
                getOwnerOperationsView().refreshPanel();
            }
        });
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public OperationsView<Owner> getOwnerOperationsView() {
        return ownerOperationsView;
    }

    public void disposeWindow() {
        this.dispose();
    }
}
