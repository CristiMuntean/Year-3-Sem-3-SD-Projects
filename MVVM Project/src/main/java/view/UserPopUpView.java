package view;

import model.User;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.UserPopUpVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPopUpView extends JFrame {
    private static final UserPopUpVM userPopUpVM = UserPopUpVM.getVMInterface();
    private final OperationsView<User> userOperationsView;
    private JPanel contentPane;
    private JPanel popUpPanel;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    @Bind(value = "text", target = "idTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField idTextField;
    @Bind(value = "text", target = "usernameTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField usernameTextField;
    @Bind(value = "text", target = "passwordTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField passwordTextField;
    @Bind(value = "text", target = "roleTextField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField roleTextField;
    private JButton submitButton;

    public UserPopUpView(String name, User selectedUser, OperationsView<User> userOperationsView) {
        super(name);
        userPopUpVM.setSelectedUser(selectedUser);
        this.userOperationsView = userOperationsView;
        this.prepareGui(name);

        try {
            Binder.bind(this, userPopUpVM);
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
        this.usernameLabel = new JLabel("username");
        this.usernameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.usernameLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.usernameTextField = new JTextField();
        this.usernameTextField.setColumns(10);
        this.popUpPanel.add(this.usernameTextField, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        this.passwordLabel = new JLabel("password");
        this.passwordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.passwordLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.passwordTextField = new JTextField();
        this.passwordTextField.setColumns(10);
        this.popUpPanel.add(this.passwordTextField, c);

        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 1;
        this.roleLabel = new JLabel("role");
        this.roleLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.popUpPanel.add(this.roleLabel, c);
        c.gridx = 1;
        c.gridwidth = 3;
        this.roleTextField = new JTextField();
        this.roleTextField.setColumns(10);
        this.popUpPanel.add(this.roleTextField, c);

        c.gridy = 5;
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().contains("Add")) {
                    userPopUpVM.getUserCommandInsert().execute();
                } else {
                    userPopUpVM.getUserCommandUpdate().execute();
                }
                disposeWindow();
                getUserOperationsView().refreshPanel();
            }
        });
        this.submitButton.setActionCommand(name);
        this.popUpPanel.add(this.submitButton, c);

        this.contentPane.add(this.popUpPanel);
    }

    public void disposeWindow() {
        this.dispose();
    }

    public OperationsView<User> getUserOperationsView() {
        return userOperationsView;
    }
}
