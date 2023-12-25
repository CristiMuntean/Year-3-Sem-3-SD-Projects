package view;

import controller.OperationsController;
import model.language.Languages;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class OperationsView<T> extends JFrame implements OperationsInterface<T>, Observer {
    private JPanel contentPane;
    private JPanel operationsPanel;
    private final Class<T> operationClass;
    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
    private JLabel titleLabel;
    private JTable operationsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private final OperationsController operationsController;
    private final UserInterface userInterface;

    public OperationsView(String name, Class operationClass, UserInterface userInterface) {
        super(name);
        this.operationClass = operationClass;
        operationsController = new OperationsController<T>(operationClass, this);
        operationsController.getLanguage().addObserver(this);
        this.userInterface = userInterface;
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.prepareOperationsPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareOperationsPanel() {
        this.operationsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.changeLanguageEnglishButton = new JButton(operationsController.getLanguage().getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageEnglishButton.addActionListener(operationsController);
        this.changeLanguageEnglishButton.setActionCommand("ENGLISH");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!operationsController.getLanguage().getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.operationsPanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(operationsController.getLanguage().getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageRomanianButton.addActionListener(operationsController);
        this.changeLanguageRomanianButton.setActionCommand("ROMANIAN");

        if(!operationsController.getLanguage().getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.operationsPanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(operationsController.getLanguage().getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageItalianButton.addActionListener(operationsController);
        this.changeLanguageItalianButton.setActionCommand("ITALIAN");

        if(!operationsController.getLanguage().getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.operationsPanel.add(this.changeLanguageItalianButton,c);
        }

        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        this.titleLabel = new JLabel(operationsController.prepareTitle());
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.operationsPanel.add(this.titleLabel, c);

        this.operationsTable = operationsController.getTable();
        JScrollPane scrollPane = new JScrollPane(this.operationsTable);
        this.operationsTable.setFillsViewportHeight(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridheight = 3;
        c.gridy = 2;
        this.operationsPanel.add(scrollPane, c);

        this.addButton = new JButton(operationsController.prepareAddButton());
        this.addButton.addActionListener(operationsController);
        this.addButton.setActionCommand("ADD");
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 5;
        this.operationsPanel.add(this.addButton, c);

        this.updateButton = new JButton(operationsController.prepareEditButton());
        this.updateButton.addActionListener(operationsController);
        this.updateButton.setActionCommand("UPDATE");
        c.gridy = 3;
        this.operationsPanel.add(this.updateButton, c);

        this.deleteButton = new JButton(operationsController.prepareDeleteButton());
        this.deleteButton.addActionListener(operationsController);
        this.deleteButton.setActionCommand("DELETE");
        c.gridy = 4;
        this.operationsPanel.add(this.deleteButton, c);

        this.contentPane.add(this.operationsPanel);
    }

    private void clearPanel() {
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.operationsPanel.revalidate();
        this.contentPane.repaint();
    }

    public void refreshPanel() {
        this.clearPanel();
        this.prepareGui();
    }

    public Class<T> getOperationClass() {
        return operationClass;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public JTable getOperationsTable() {
        return operationsTable;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel();
        userInterface.refreshPanel();
    }
}
