package view;

import controller.ChartController;
import model.language.Languages;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ChartView extends JFrame implements Observer {
    private JPanel contentPane;

    private JButton changeLanguageEnglishButton;
    private JButton changeLanguageRomanianButton;
    private JButton changeLanguageItalianButton;
    private JPanel chartPanel;
    private JFreeChart statisticsChart;
    private JButton brandButton;
    private JButton fuelButton;
    private JButton colorButton;
    private final ChartController chartController = ChartController.getControllerInstance(this);
    private final EmployeeInterface employeeInterface;

    public ChartView(String name, EmployeeInterface employeeInterface) {
        super(name);
        this.chartController.getLanguage().addObserver(this);
        this.statisticsChart = chartController.createBrandPieChart();
        this.employeeInterface = employeeInterface;
        prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(1100, 800));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1, 1));
        this.prepareChartPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareChartPanel() {
        this.chartPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.fill = GridBagConstraints.HORIZONTAL;
        this.changeLanguageEnglishButton = new JButton(chartController.getLanguage().getFields().getChangeLanguageEnglishButton());
        this.changeLanguageEnglishButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageEnglishButton.addActionListener(chartController);
        this.changeLanguageEnglishButton.setActionCommand("ENGLISH");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        if(!chartController.getLanguage().getSelectedLanguage().equals(Languages.ENGLISH)) {
            this.chartPanel.add(this.changeLanguageEnglishButton,c);
            c.gridx += 3;
        }

        this.changeLanguageRomanianButton = new JButton(chartController.getLanguage().getFields().getChangeLanguageRomanianButton());
        this.changeLanguageRomanianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageRomanianButton.addActionListener(chartController);
        this.changeLanguageRomanianButton.setActionCommand("ROMANIAN");

        if(!chartController.getLanguage().getSelectedLanguage().equals(Languages.ROMANIAN)) {
            this.chartPanel.add(this.changeLanguageRomanianButton,c);
            c.gridx += 3;
        }

        this.changeLanguageItalianButton = new JButton(chartController.getLanguage().getFields().getChangeLanguageItalianButton());
        this.changeLanguageItalianButton.setFont(new Font("Sans-Serif",Font.PLAIN,10));
        this.changeLanguageItalianButton.addActionListener(chartController);
        this.changeLanguageItalianButton.setActionCommand("ITALIAN");

        if(!chartController.getLanguage().getSelectedLanguage().equals(Languages.ITALIAN)) {
            this.chartPanel.add(this.changeLanguageItalianButton,c);
        }

        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 4;
        this.chartPanel.add(new ChartPanel(this.statisticsChart),c);

        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridy = 5;
        this.brandButton = new JButton(chartController.getLanguage().getFields().getBrandsStatisticsButton());
        this.brandButton.addActionListener(chartController);
        this.brandButton.setActionCommand("BRAND");
        this.chartPanel.add(this.brandButton,c);

        c.gridx=2;
        this.fuelButton = new JButton(chartController.getLanguage().getFields().getFuelStatisticsButton());
        this.fuelButton.addActionListener(chartController);
        this.fuelButton.setActionCommand("FUEL");
        this.chartPanel.add(this.fuelButton,c);

        c.gridx=4;
        this.colorButton = new JButton(chartController.getLanguage().getFields().getColorStatisticsButton());
        this.colorButton.addActionListener(chartController);
        this.colorButton.setActionCommand("COLOR");
        this.chartPanel.add(this.colorButton,c);

        this.contentPane.add(this.chartPanel);
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

    public void setStatisticsChart(JFreeChart statisticsChart) {
        this.statisticsChart = statisticsChart;
    }

    public JFreeChart getStatisticsChart() {
        return statisticsChart;
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshPanel();
        employeeInterface.refreshPanel();

    }
}
