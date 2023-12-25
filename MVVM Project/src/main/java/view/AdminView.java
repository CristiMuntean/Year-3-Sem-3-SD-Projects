package view;

import model.User;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.AdminVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminView extends JFrame {
    private static final AdminVM adminVM = AdminVM.getAdminVMInstance();
    @Bind(value = "model", target = "filterModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedFilter.value", type = BindingType.BI_DIRECTIONAL)
    private final JComboBox<String> filterComboBox;
    private JPanel contentPane;
    private JPanel adminPanel;
    private JLabel titleLabel;
    private JTable usersTable;
    private JButton userOperationsButton;
    private JButton logOutButton;
    private JLabel filterLabel;

    public AdminView(String name) {
        super(name);
        this.filterComboBox = new JComboBox<>();
        this.prepareGui();
        try {
            Binder.bind(this, adminVM);
        } catch (BindingException e) {
            throw new RuntimeException(e);
        }

        this.filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminVM.getFilterCommand().execute();
                clearPanel();
                refreshPanel();
            }
        });
    }

    private void prepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));


        this.prepareAdminPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareAdminPanel() {
        this.adminPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 0;

        this.titleLabel = new JLabel("Admin Window");
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.adminPanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        List<User> userList = adminVM.getAllUsers();
        userList = adminVM.filterList(userList);
        this.usersTable = adminVM.getUsersTable(userList);
        JScrollPane scrollPane = new JScrollPane(this.usersTable);
        this.usersTable.setFillsViewportHeight(true);
        c.gridheight = 2;
        c.gridy = 1;
        this.adminPanel.add(scrollPane, c);

        this.filterLabel = new JLabel("Filter users by:");
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        this.adminPanel.add(this.filterLabel, c);


        c.gridx = 3;
        this.adminPanel.add(this.filterComboBox, c);

        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        this.userOperationsButton = new JButton("User operations");
        this.userOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminVM.getUserOperationsCommand().execute();
            }
        });
        this.adminPanel.add(this.userOperationsButton, c);

        c.gridx = 3;
        this.logOutButton = new JButton("Log out");
        this.logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeWindow();
                adminVM.getLogOutCommand().execute();
            }
        });
        this.adminPanel.add(this.logOutButton, c);

        this.contentPane.add(this.adminPanel);
    }

    public void disposeWindow() {
        this.dispose();
    }

    private void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    public void refreshPanel() {
        this.clearPanel();
        this.prepareGui();
    }
}
