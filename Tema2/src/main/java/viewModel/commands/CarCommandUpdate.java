package viewModel.commands;

import model.Car;
import model.persistence.CarPersistence;
import viewModel.CarPopUpVM;

public class CarCommandUpdate implements CommandInterface {

    private final CarPersistence carPersistence = new CarPersistence();
    private final CarPopUpVM carPopUpVM;

    public CarCommandUpdate(CarPopUpVM carPopUpVM) {
        this.carPopUpVM = carPopUpVM;
    }

    @Override
    public boolean execute() {
        Car selectedCar = carPopUpVM.getSelectedCar();
        Car car = new Car(
                carPopUpVM.getIdTextField(),
                carPopUpVM.getBrandTextField(),
                carPopUpVM.getModelTextField(),
                carPopUpVM.getColorTextField(),
                carPopUpVM.getFuelTextField()
        );
        carPersistence.updateCar(car, selectedCar);
        return false;
    }
}
