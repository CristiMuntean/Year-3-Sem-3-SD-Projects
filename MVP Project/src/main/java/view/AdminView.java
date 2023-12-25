package view;

import model.User;
import presenter.AdminPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminView extends JFrame implements ActionListener, UserInterface {
    private JPanel contentPane;
    private JPanel adminPanel;
    private JLabel titleLabel;
    private JTable usersTable;
    private JButton userOperationsButton;
    private JButton logOutButton;
    private final AdminPresenter adminPresenter = AdminPresenter.getAdminPresenterInstance();

    public AdminView(String name) {
        super(name);
        this.prepareGui();
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
        List<User> userList = adminPresenter.getAllUsers();
        this.usersTable = adminPresenter.getUsersTable(userList);
        JScrollPane scrollPane = new JScrollPane(this.usersTable);
        this.usersTable.setFillsViewportHeight(true);
        c.gridheight = 2;
        c.gridy = 1;
        this.adminPanel.add(scrollPane, c);

        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 3;
        this.userOperationsButton = new JButton("User operations");
        this.userOperationsButton.addActionListener(this);
        this.userOperationsButton.setActionCommand("USER_OPERATIONS");
        this.adminPanel.add(this.userOperationsButton, c);

        c.gridx = 3;
        this.logOutButton = new JButton("Log out");
        this.logOutButton.addActionListener(this);
        this.logOutButton.setActionCommand("LOG_OUT");
        this.adminPanel.add(this.logOutButton, c);

        this.contentPane.add(this.adminPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "USER_OPERATIONS" -> {
                OperationsView<User> operationsView = new OperationsView<>("User operations", User.class, this);
                operationsView.setVisible(true);
                operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            case "LOG_OUT" -> {
                this.dispose();
                LoginView loginView = new LoginView("Log in");
                loginView.setVisible(true);
                loginView.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        }
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
