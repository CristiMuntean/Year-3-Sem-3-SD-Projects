package viewModel.commands;

import model.Car;
import model.persistence.CarPersistence;
import viewModel.AbstractEmployeeVM;
import viewModel.EmployeeVM;
import viewModel.ManagerVM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveCommand implements CommandInterface {
    private final AbstractEmployeeVM employeeVM;

    public SaveCommand(AbstractEmployeeVM employeeVM) {
        this.employeeVM = employeeVM;
    }

    @Override
    public boolean execute() {
        String format = employeeVM instanceof EmployeeVM ? ((EmployeeVM) employeeVM).getSelectedSaveField() : ((ManagerVM) employeeVM).getSelectedSaveField();
        CarPersistence carPersistence = new CarPersistence();
        List<Car> carList = carPersistence.selectAllCars();
        switch (format) {
            case "csv" -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Id,brand,modelName,color,fuelType\n");
                for (Car car : carList) {
                    sb.append(String.join(",",
                            Arrays.stream(car.toString().split(",")).map(el -> el.split(":")[1]).collect(Collectors.toList()))).append("\n");
                }
                try {
                    File file = new File("car_info.csv");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e) {
                    return false;
                }
            }
            case "json" -> {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n    \"carList\": {\n");
                for (Car car : carList) {
                    String carJson = "        \"car\": {\n            " + String.join(",\n            ",
                            Arrays.stream(car.toString().split(","))
                                    .map(el -> "\"" + el.split(":")[0] + "\": \"" + el.split(":")[1] + "\"").collect(Collectors.toList())) + "\n        },\n";
                    sb.append(carJson);
                }
                sb.deleteCharAt(sb.length() - 2);
                sb.append("    }\n}");
                try {
                    File file = new File("car_info.json");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e) {
                    return false;
                }
            }
            case "xml" -> {
                StringBuilder sb = new StringBuilder();
                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                sb.append("<carList>\n");
                for (Car car : carList) {
                    sb.append("    <car>\n");
                    String carXML = String.join("\n",
                            Arrays.stream(car.toString().split(",")).map(el -> "        <" + el.split(":")[0] + ">" + el.split(":")[1] + "</" + el.split(":")[0] + ">").collect(Collectors.toList()));
                    sb.append(carXML).append("\n    </car>\n");
                }
                sb.append("</carList>");
                try {
                    File file = new File("car_info.xml");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e) {
                    return false;
                }
            }
            case "txt" -> {
                StringBuilder sb = new StringBuilder();
                for (Car car : carList) {
                    sb.append(car).append("\n");
                }
                try {
                    File file = new File("car_info.txt");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
