package viewModel.commands;

import model.Car;
import view.CarsPopUpView;
import viewModel.AbstractEmployeeVM;
import viewModel.EmployeeVM;
import viewModel.ManagerVM;

import javax.swing.*;
import java.util.List;

public class SearchCarsCommand implements CommandInterface {

    private final AbstractEmployeeVM abstractEmployeeVM;

    public SearchCarsCommand(AbstractEmployeeVM abstractEmployeeVM) {
        this.abstractEmployeeVM = abstractEmployeeVM;
    }

    @Override
    public boolean execute() {
        if (abstractEmployeeVM instanceof EmployeeVM) {
            if (!((EmployeeVM) abstractEmployeeVM).getSelectedSearchField().equals("Owner")) {
                List<Car> cars = abstractEmployeeVM.searchCarsByOwner(((EmployeeVM) abstractEmployeeVM).getSelectedSearchField());
                JTable carsTable = abstractEmployeeVM.getCarsTable(cars);
                CarsPopUpView carsPopUpView = new CarsPopUpView("Searched cars", carsTable);
                carsPopUpView.setVisible(true);
            }
        } else {
            if (!((ManagerVM) abstractEmployeeVM).getSelectedSearchField().equals("Owner")) {
                List<Car> cars = abstractEmployeeVM.searchCarsByOwner(((ManagerVM) abstractEmployeeVM).getSelectedSearchField());
                JTable carsTable = abstractEmployeeVM.getCarsTable(cars);
                CarsPopUpView carsPopUpView = new CarsPopUpView("Searched cars", carsTable);
                carsPopUpView.setVisible(true);
            }
        }
        return true;
    }
}
