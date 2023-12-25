package view;

import model.Car;
import model.CarJoinedOwner;
import model.Owner;
import model.ServiceLog;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;
import viewModel.ManagerVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ManagerView extends JFrame {
    private JPanel contentPane;
    private JPanel managerPanel;
    private JLabel titleLabel;
    private JTable carsTable;
    private JLabel sortLabel;

    @Bind(value = "selected", target = "sortBrandField.value", type = BindingType.BI_DIRECTIONAL)
    private JCheckBox sortBrandCheckBox;
    @Bind(value = "selected", target = "sortFuelField.value", type = BindingType.BI_DIRECTIONAL)
    private JCheckBox sortFuelCheckBox;

    private JLabel filterLabel;
    @Bind(value = "model", target = "filterOwnerModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedFilterOwnerField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> filterOwnerComboBox;
    @Bind(value = "model", target = "filterBrandModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedFilterBrandField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> filterBrandComboBox;
    @Bind(value = "model", target = "filterFuelModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedFilterFuelField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> filterFuelTypeComboBox;
    @Bind(value = "model", target = "filterColorModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedFilterColorField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> filterColorComboBox;
    private JLabel searchLabel;
    @Bind(value = "model", target = "searchModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedSearchField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> searchComboBox;
    @Bind(value = "model", target = "saveModel.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedItem", target = "selectedSaveField.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> saveComboBox;
    private JButton saveButton;
    private JButton logOutButton;
    private final ManagerVM managerVM = ManagerVM.getManagerVMInstance();

    public ManagerView(String name) {
        super(name);
        this.prepareGui();

        try {
            Binder.bind(this, managerVM);
        } catch (BindingException e) {
            throw new RuntimeException(e);
        }

        this.sortBrandCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getSortOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        this.sortFuelCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getSortOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        filterOwnerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getFilteringOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        filterBrandComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getFilteringOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        filterFuelTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getFilteringOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        filterColorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getFilteringOptionsCommand().execute();
                clearPanel();
                reprepareGui();
            }
        });
        this.searchComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getSearchCommand().execute();
            }
        });
    }

    private void prepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.sortBrandCheckBox = new JCheckBox("Brand");
        this.sortBrandCheckBox.setSelected(false);

        this.sortFuelCheckBox = new JCheckBox("Fuel Type");
        this.sortFuelCheckBox.setSelected(false);


        this.filterOwnerComboBox = new JComboBox<>();
        this.filterBrandComboBox = new JComboBox<>();
        this.filterFuelTypeComboBox = new JComboBox<>();
        this.filterColorComboBox = new JComboBox<>();
        this.searchComboBox = new JComboBox<>();


        this.prepareManagerPanel();
        this.setContentPane(this.contentPane);
    }

    private void reprepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareManagerPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareManagerPanel() {
        this.managerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 0;

        this.titleLabel = new JLabel("Manager View");
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.managerPanel.add(this.titleLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        List<CarJoinedOwner> carsWithOwners = managerVM.getAllCarsWithOwners();

        carsWithOwners = managerVM.sortList(carsWithOwners);
        carsWithOwners = managerVM.filterList(carsWithOwners);

        this.carsTable = managerVM.getCarsWithOwnersTable(carsWithOwners);
        JScrollPane scrollPane = new JScrollPane(this.carsTable);
        this.carsTable.setFillsViewportHeight(true);
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        this.managerPanel.add(scrollPane, c);

        this.sortLabel = new JLabel("Sort car table by:");
        this.sortLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        this.managerPanel.add(this.sortLabel, c);

        c.gridx = 3;
        c.gridwidth = 1;
        this.managerPanel.add(this.sortBrandCheckBox,c);
        c.gridx = 4;
        this.managerPanel.add(this.sortFuelCheckBox,c);



        this.filterLabel = new JLabel("Select filters to apply on the table above: ");
        this.filterLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 4;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        this.managerPanel.add(this.filterLabel, c);

        c.gridwidth = 1;
        c.gridx = 2;
        this.managerPanel.add(filterOwnerComboBox,c);
        c.gridx = 3;
        this.managerPanel.add(filterBrandComboBox,c);
        c.gridx = 4;
        this.managerPanel.add(filterFuelTypeComboBox,c);
        c.gridx = 5;
        this.managerPanel.add(filterColorComboBox,c);

        this.searchLabel = new JLabel("Search cars by owner:");
        this.searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridx = 0;
        this.managerPanel.add(this.searchLabel,c);


        c.gridwidth = 3;
        c.gridx = 2;
        this.managerPanel.add(this.searchComboBox,c);

        c.gridy = 6;
        c.gridx = 0;
        this.saveButton = new JButton("Save car info in format");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerVM.getSaveCommand().execute();
            }
        });
        this.managerPanel.add(this.saveButton,c);

        c.gridx = 3;
        this.saveComboBox = new JComboBox<>(new String[]{"csv","json","xml","txt"});
        this.managerPanel.add(this.saveComboBox,c);

        c.gridy = 7;
        c.gridx = 0;
        this.logOutButton = new JButton("Log out");
        this.logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeWindow();
                managerVM.getLogOutCommand().execute();
            }
        });
        this.managerPanel.add(this.logOutButton, c);

        this.contentPane.add(this.managerPanel);
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

    public void disposeWindow() {
        this.dispose();
    }
}
