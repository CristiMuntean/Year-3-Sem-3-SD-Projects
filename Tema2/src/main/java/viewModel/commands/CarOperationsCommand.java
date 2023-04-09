package viewModel.commands;

import model.Car;
import view.OperationsView;

import javax.swing.*;

public class CarOperationsCommand implements CommandInterface {
    @Override
    public boolean execute() {
        OperationsView<Car> operationsView = new OperationsView<>("Car operations", Car.class);
        operationsView.setVisible(true);
        operationsView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return true;
    }
}
