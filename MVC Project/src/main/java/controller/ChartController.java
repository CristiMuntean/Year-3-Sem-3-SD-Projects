package controller;

import model.Car;
import model.language.Languages;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import persistence.CarPersistence;
import view.ChartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChartController implements ActionListener {
    private static ChartController instance;
    private final CarPersistence carPersistence;
    private ChartView chartView;
    private Language language;
    private ChartController() {
        carPersistence = new CarPersistence();
    }

    public static ChartController getControllerInstance(ChartView chartView) {
        if(instance == null)
            instance = new ChartController();
        instance.setChartView(chartView);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public JFreeChart createBrandPieChart() {
        List<Car> carList = carPersistence.selectAllCars();
        List<String> distinctBrandList = carList.stream().map(Car::getBrand).distinct().toList();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(String brand: distinctBrandList) {
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
        List<Car> carList = carPersistence.selectAllCars();
        List<String> distinctFuelList = carList.stream().map(Car::getFuelType).distinct().toList();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(String fuel: distinctFuelList) {
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
        List<Car> carList = carPersistence.selectAllCars();
        List<String> distinctColorList = carList.stream().map(Car::getColor).distinct().toList();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(String color: distinctColorList) {
            int number = carList.stream().filter(el -> el.getColor().equals(color)).toList().size();
            dataset.addValue(number,color,language.getFields().getColorStatisticsColorAxisLabel());
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
                if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
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
                if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
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
                if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getBrandsStatisticsTitle()))
                    currentChart = "Brands";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getFuelStatisticsTitle()))
                    currentChart = "Fuel";
                else if(chartView.getStatisticsChart().getTitle().getText().equals(language.getFields().getColorStatisticsTitle()))
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
