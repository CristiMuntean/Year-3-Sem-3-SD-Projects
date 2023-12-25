package car_domain.controller;

import car_domain.model.Car;
import car_domain.view.ChartView;
import client.ProxyClient;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import user_domain.view.EmployeeInterface;
import user_domain.view.language.Language;
import user_domain.view.language.Languages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ChartController implements ActionListener {
    private ChartView chartView;
    private final Language language;
    private final ProxyClient proxyClient;

    public ChartController(ProxyClient proxyClient, EmployeeInterface employeeInterface) {

        language = Language.getLanguageInstance();
        this.proxyClient = proxyClient;
        chartView = new ChartView(language.getFields().getManagerStatisticsButton(), employeeInterface, this);
        chartView.setVisible(true);
        chartView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private List<Car> getCarList() {
        return proxyClient.getCarList();
    }

    public JFreeChart createBrandPieChart() {
        List<Car> carList = getCarList();
        List<String> distinctBrandList = carList.stream().map(Car::getBrand).distinct().toList();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String brand : distinctBrandList) {
            int number = carList.stream().filter(el -> el.getBrand().equals(brand)).toList().size();
            dataset.setValue(brand, number);
        }
        return ChartFactory.createPieChart(
                language.getFields().getBrandsStatisticsTitle(),
                dataset,
                true,
                true,
                false
        );
    }

    public JFreeChart createFuelRingChart() {
        List<Car> carList = getCarList();
        List<String> distinctFuelList = carList.stream().map(Car::getFuelType).distinct().toList();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String fuel : distinctFuelList) {
            int number = carList.stream().filter(el -> el.getFuelType().equals(fuel)).toList().size();
            dataset.setValue(fuel, number);
        }

        return ChartFactory.createRingChart(
                language.getFields().getFuelStatisticsTitle(),
                dataset,
                true,
                true,
                false
        );
    }

    public JFreeChart createColorBarChart() {
        List<Car> carList = getCarList();
        List<String> distinctColorList = carList.stream().map(Car::getColor).distinct().toList();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String color : distinctColorList) {
            int number = carList.stream().filter(el -> el.getColor().equals(color)).toList().size();
            dataset.addValue(number, color, language.getFields().getColorStatisticsColorAxisLabel());
        }

        return ChartFactory.createBarChart(
                language.getFields().getColorStatisticsTitle(),
                language.getFields().getColorStatisticsColorAxisLabel(),
                language.getFields().getColorStatisticsNumberOfCarsAxisLabel(),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "BRAND" -> {
                chartView.setStatisticsChart(createBrandPieChart());
                chartView.refreshPanel();
            }
            case "FUEL" -> {
                chartView.setStatisticsChart(createFuelRingChart());
                chartView.refreshPanel();
            }
            case "COLOR" -> {
                chartView.setStatisticsChart(createColorBarChart());
                chartView.refreshPanel();
            }
            case "ENGLISH" -> {
                String currentChart = null;
                if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
                    currentChart = "Color";
                language.setSelectedLanguage(Languages.ENGLISH);
                switch (currentChart) {
                    case "Brands" -> chartView.setStatisticsChart(createBrandPieChart());
                    case "Fuel" -> chartView.setStatisticsChart(createFuelRingChart());
                    case "Color" -> chartView.setStatisticsChart(createColorBarChart());
                }
                language.setSelectedLanguage(Languages.ENGLISH);
            }
            case "ROMANIAN" -> {
                String currentChart = null;
                if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
                    currentChart = "Color";
                language.setSelectedLanguage(Languages.ROMANIAN);
                switch (currentChart) {
                    case "Brands" -> chartView.setStatisticsChart(createBrandPieChart());
                    case "Fuel" -> chartView.setStatisticsChart(createFuelRingChart());
                    case "Color" -> chartView.setStatisticsChart(createColorBarChart());
                }
                language.setSelectedLanguage(Languages.ROMANIAN);
            }
            case "ITALIAN" -> {
                String currentChart = null;
                if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if (chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
                    currentChart = "Color";
                language.setSelectedLanguage(Languages.ITALIAN);
                switch (currentChart) {
                    case "Brands" -> chartView.setStatisticsChart(createBrandPieChart());
                    case "Fuel" -> chartView.setStatisticsChart(createFuelRingChart());
                    case "Color" -> chartView.setStatisticsChart(createColorBarChart());
                }
                language.setSelectedLanguage(Languages.ITALIAN);
            }
        }
    }

    public void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }

    public Language getLanguage() {
        return language;
    }
}
