package user_domain.view;

import user_domain.controller.AdminController;
import user_domain.view.language.Language;
import user_domain.view.language.Languages;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AdminView extends JFrame implements EmployeeInterface, Observer {
    private JPanel contentPane;
    private JPanel adminPanel;
    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
    private JLabel titleLabel;
    private JTable usersTable;
    private JButton userOperationsButton;
    private JLabel filterLabel;
    private JComboBox<String> filterComboBox;
    private JButton logOutButton;
    private final AdminController adminController;

    public AdminView(String name, AdminController adminController, JTable usersTable) {
        super(name);
        this.adminController = adminController;
        adminController.getLanguage().addObserver(this);
        this.prepareGui(usersTable);
    }

    private void prepareGui(JTable usersTable) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareAdminPanel(usersTable, Language.getLanguageInstance().getFields().getAdminFilterComboBox());
        this.setContentPane(this.contentPane);
    }

    @Override
    public void reprepareGui(JTable carsWithOwnersTable, String filterOptions) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareAdminPanel(carsWithOwnersTable, filterOptions);
        this.setContentPane(this.contentPane);
    }

    @Override
    public List<JComboBox<String>> getFilterComboBoxes() {
        return null;
    }

    @Override
    public JComboBox<String> getSearchComboBox() {
        return null;
    }

    @Override
    public JComboBox<String> getSaveComboBox() {
        return null;
    }

    private void prepareAdminPanel(JTable usersTable, String filterOptions) {
        this.adminPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.fill = GridBagConstraints.HORIZONTAL;
        this.changeLanguageEnglishButton = new JButton(adminController.getLanguage().getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageEnglishButton.addActionListener(adminController);
        this.changeLanguageEnglishButton.setActionCommand("ENGLISH");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!adminController.getLanguage().getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.adminPanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(adminController.getLanguage().getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageRomanianButton.addActionListener(adminController);
        this.changeLanguageRomanianButton.setActionCommand("ROMANIAN");

        if(!adminController.getLanguage().getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.adminPanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(adminController.getLanguage().getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageItalianButton.addActionListener(adminController);
        this.changeLanguageItalianButton.setActionCommand("ITALIAN");

        if(!adminController.getLanguage().getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.adminPanel.add(this.changeLanguageItalianButton,c);
        }


        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.CENTER;
        this.titleLabel = new JLabel(adminController.getLanguage().getFields().getAdminTitleLabel());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.adminPanel.add(this.titleLabel, c);

        List<String> distinctTypeList = adminController.getDistinctTypes();
        distinctTypeList.add(0, adminController.getLanguage().getFields().getAdminFilterComboBox());
        this.filterComboBox = new JComboBox<>(distinctTypeList.toArray(new String[distinctTypeList.size()]));
        if(!filterOptions.equals(Language.getLanguageInstance().getFields().getAdminFilterComboBox())) {
            this.filterComboBox.setSelectedItem(filterOptions);
        }

        this.filterComboBox.addActionListener(adminController);
        this.filterComboBox.setSelectedItem(adminController.getSelectedRole());
        this.filterComboBox.setActionCommand("FILTER");

        c.fill = GridBagConstraints.HORIZONTAL;
        this.usersTable = usersTable;
        JScrollPane scrollPane = new JScrollPane(this.usersTable);
        this.usersTable.setFillsViewportHeight(true);
        c.gridheight = 2;
        c.gridy = 2;
        this.adminPanel.add(scrollPane, c);

        this.filterLabel = new JLabel(adminController.getLanguage().getFields().getAdminFilterLabel());
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        this.adminPanel.add(this.filterLabel, c);


        c.gridx = 3;
        this.adminPanel.add(this.filterComboBox, c);

        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        this.userOperationsButton = new JButton(adminController.getLanguage().getFields().getAdminUserOperationsButton());
        this.userOperationsButton.addActionListener(adminController);
        this.userOperationsButton.setActionCommand("USER_OPERATIONS");
        this.adminPanel.add(this.userOperationsButton, c);

        c.gridx = 3;
        this.logOutButton = new JButton(adminController.getLanguage().getFields().getLogOutButton());
        this.logOutButton.addActionListener(adminController);
        this.logOutButton.setActionCommand("LOG_OUT");
        this.adminPanel.add(this.logOutButton, c);

        this.contentPane.add(this.adminPanel);
    }

    @Override
    public List<JCheckBox> getSortCheckBoxes() {
        return null;
    }

    @Override
    public void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    @Override
    public void refreshPanel() {
        this.clearPanel();
        JTable usersTable = adminController.getUsersTable();
        this.prepareGui(usersTable);
    }

    @Override
    public void refreshPanel(JTable usersTable, String filterOptions) {
        this.clearPanel();
        this.reprepareGui(usersTable, filterOptions);
    }

    @Override
    public void disposeWindow() {
        this.dispose();
    }

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel();
    }
}
