package car_domain.view;

import javax.swing.*;
import java.awt.*;

public class CarsPopUpView extends JFrame {
    private JPanel contentPane;
    private JPanel carsPanel;
    private JTable carsTable;


    public CarsPopUpView(String name, JTable objectTable) {
        super(name);
        this.prepareGui(objectTable);
    }

    private void prepareGui(JTable objectTable) {
        this.setSize(new Dimension(600, 500));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));

        this.carsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridheight = 4;
        this.carsTable = objectTable;
        JScrollPane scrollPane = new JScrollPane(this.carsTable);
        this.carsTable.setFillsViewportHeight(true);
        this.carsPanel.add(scrollPane, c);

        this.contentPane.add(this.carsPanel);
        this.setContentPane(this.contentPane);
    }
}
