package user_domain.view;

import user_domain.controller.ManagerController;
import user_domain.view.language.Languages;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class ManagerView extends JFrame implements EmployeeInterface, Observer {
    private JPanel contentPane;
    private JPanel managerPanel;

    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
    private JLabel titleLabel;
    private JTable carsTable;
    private JLabel sortLabel;
    private List<JCheckBox> sortCheckBoxes;
    private JLabel filterLabel;
    private List<JComboBox<String>> filterComboBoxes;
    private JLabel searchLabel;
    private JComboBox<String> searchComboBox;

    private JComboBox<String> saveComboBox;
    private JButton saveButton;
    private JButton statisticsButton;
    private JButton logOutButton;
    private final ManagerController managerController;

    public ManagerView(String name, ManagerController managerController, JTable carsWithOwnersTable) {
        super(name);
        this.managerController = managerController;
        managerController.getLanguage().addObserver(this);
        this.prepareGui(carsWithOwnersTable);
    }

    private void prepareGui(JTable carsWithOwnersTable) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.sortCheckBoxes = new ArrayList<>();
        this.sortCheckBoxes.add(new JCheckBox(managerController.getLanguage().getFields().getSortBrandCheckBox()));
        this.sortCheckBoxes.add(new JCheckBox(managerController.getLanguage().getFields().getSortFuelTypeCheckBox()));

        this.sortCheckBoxes.get(0).setSelected(false);
        this.sortCheckBoxes.get(0).addActionListener(managerController);
        this.sortCheckBoxes.get(0).setActionCommand("SORT");
        this.sortCheckBoxes.get(1).setSelected(false);
        this.sortCheckBoxes.get(1).addActionListener(managerController);
        this.sortCheckBoxes.get(1).setActionCommand("SORT");

        this.filterComboBoxes = new ArrayList<>(4);
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());
        this.filterComboBoxes.add(new JComboBox<>());

        this.filterComboBoxes.get(0).setSelectedItem(managerController.getLanguage().getFields().getFilterOwnerComboBox());
        this.filterComboBoxes.get(1).setSelectedItem(managerController.getLanguage().getFields().getFilterBrandComboBox());
        this.filterComboBoxes.get(2).setSelectedItem(managerController.getLanguage().getFields().getFilterFuelTypeComboBox());
        this.filterComboBoxes.get(3).setSelectedItem(managerController.getLanguage().getFields().getFilterColorComboBox());

        this.prepareManagerPanel(carsWithOwnersTable, "None");
        this.setContentPane(this.contentPane);
    }

    @Override
    public void reprepareGui(JTable carsWithOwnersTable, String filterOptions) {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareManagerPanel(carsWithOwnersTable, filterOptions);
        this.setContentPane(this.contentPane);
    }

    private void prepareManagerPanel(JTable carsWithOwnersTable, String filterOptions) {
        this.managerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.fill = GridBagConstraints.HORIZONTAL;
        this.changeLanguageEnglishButton = new JButton(managerController.getLanguage().getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageEnglishButton.addActionListener(managerController);
        this.changeLanguageEnglishButton.setActionCommand("ENGLISH");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!managerController.getLanguage().getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.managerPanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(managerController.getLanguage().getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageRomanianButton.addActionListener(managerController);
        this.changeLanguageRomanianButton.setActionCommand("ROMANIAN");

        if(!managerController.getLanguage().getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.managerPanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(managerController.getLanguage().getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageItalianButton.addActionListener(managerController);
        this.changeLanguageItalianButton.setActionCommand("ITALIAN");

        if(!managerController.getLanguage().getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.managerPanel.add(this.changeLanguageItalianButton,c);
        }

        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        this.titleLabel = new JLabel(managerController.getLanguage().getFields().getManagerTitleLabel());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.managerPanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;


        this.carsTable = carsWithOwnersTable;
        JScrollPane scrollPane = new JScrollPane(this.carsTable);
        this.carsTable.setFillsViewportHeight(true);
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 2;
        this.managerPanel.add(scrollPane, c);

        this.sortLabel = new JLabel(managerController.getLanguage().getFields().getSortLabel());
        this.sortLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        this.managerPanel.add(this.sortLabel, c);

        c.gridx = 3;
        c.gridwidth = 1;
        for (JCheckBox checkBox : this.sortCheckBoxes) {
            this.managerPanel.add(checkBox, c);
            c.gridx++;
        }

        List<String> distinctOwners = managerController.getDistinctOwners();
        distinctOwners.add(0, managerController.getLanguage().getFields().getFilterOwnerComboBox());
        this.filterComboBoxes.set(0, new JComboBox<>(distinctOwners.toArray(new String[distinctOwners.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("Owner:")) {
                this.filterComboBoxes.get(0).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        List<String> distinctBrands = managerController.getDistinctCarBrands();
        distinctBrands.add(0, managerController.getLanguage().getFields().getFilterBrandComboBox());
        this.filterComboBoxes.set(1, new JComboBox<>(distinctBrands.toArray(new String[distinctBrands.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("CarBrand:")) {
                this.filterComboBoxes.get(1).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        List<String> distinctFuelTypes = managerController.getDistinctFuelTypes();
        distinctFuelTypes.add(0, managerController.getLanguage().getFields().getFilterFuelTypeComboBox());
        this.filterComboBoxes.set(2, new JComboBox<>(distinctFuelTypes.toArray(new String[distinctFuelTypes.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("FuelType:")) {
                this.filterComboBoxes.get(2).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        List<String> distinctColors = managerController.getDistinctColors();
        distinctColors.add(0, managerController.getLanguage().getFields().getFilterColorComboBox());
        this.filterComboBoxes.set(3, new JComboBox<>(distinctColors.toArray(new String[distinctColors.size()])));
        for(String element : filterOptions.split(" ")) {
            if(element.contains("Color:")) {
                this.filterComboBoxes.get(3).setSelectedItem(element.split(":")[1].replace("_"," "));
            }
        }

        filterComboBoxes.forEach(element -> {
            element.addActionListener(managerController);
            element.setActionCommand("FILTER");
        });

        this.filterLabel = new JLabel(managerController.getLanguage().getFields().getFilterLabel());
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 5;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        this.managerPanel.add(this.filterLabel, c);

        c.gridwidth = 1;
        c.gridx = 2;
        filterComboBoxes.forEach(element -> {
            this.managerPanel.add(element, c);
            c.gridx++;
        });

        this.searchLabel = new JLabel(managerController.getLanguage().getFields().getSearchLabel());
        this.searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridx = 0;
        this.managerPanel.add(this.searchLabel,c);


        c.gridwidth = 3;
        c.gridx = 2;
        this.searchComboBox = new JComboBox<>(distinctOwners.toArray(new String[distinctOwners.size()]));
        this.searchComboBox.addActionListener(managerController);
        this.searchComboBox.setActionCommand("SEARCH");
        this.managerPanel.add(this.searchComboBox,c);

        c.gridy = 7;
        c.gridx = 0;
        this.saveButton = new JButton(managerController.getLanguage().getFields().getSaveButton());
        this.saveButton.addActionListener(managerController);
        this.saveButton.setActionCommand("SAVE");
        this.managerPanel.add(this.saveButton, c);

        c.gridx = 3;
        this.saveComboBox = new JComboBox<>(new String[]{"csv","json","xml","txt"});
        this.managerPanel.add(this.saveComboBox,c);

        c.gridy = 8;
        c.gridx = 0;
        this.statisticsButton = new JButton(managerController.getLanguage().getFields().getManagerStatisticsButton());
        this.statisticsButton.addActionListener(managerController);
        this.statisticsButton.setActionCommand("STATISTICS");
        this.managerPanel.add(this.statisticsButton,c);

        c.gridx = 3;
        this.logOutButton = new JButton(managerController.getLanguage().getFields().getLogOutButton());
        this.logOutButton.addActionListener(managerController);
        this.logOutButton.setActionCommand("LOG_OUT");
        this.managerPanel.add(this.logOutButton, c);

        this.contentPane.add(this.managerPanel);
    }

    public void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    @Override
    public List<JCheckBox> getSortCheckBoxes() {
        return sortCheckBoxes;
    }

    @Override
    public void refreshPanel() {
        this.clearPanel();
        JTable carsTableRefreshed = managerController.getCarsWithOwnersTable();
        this.reprepareGui(carsTableRefreshed, "None");
    }

    @Override
    public void refreshPanel(JTable carsWithOwnersTable, String filterOptions) {
        this.clearPanel();
        this.reprepareGui(carsWithOwnersTable, filterOptions);
    }

    @Override
    public void disposeWindow() {
        this.dispose();
    }

    @Override
    public List<JComboBox<String>> getFilterComboBoxes() {
        return filterComboBoxes;
    }

    @Override
    public JComboBox<String> getSearchComboBox() {
        return searchComboBox;
    }

    @Override
    public JComboBox<String> getSaveComboBox() {
        return saveComboBox;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel(carsTable, "None");
    }
}
