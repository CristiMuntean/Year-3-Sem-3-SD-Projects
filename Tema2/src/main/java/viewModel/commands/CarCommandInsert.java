package viewModel.commands;

import model.Car;
import model.persistence.CarPersistence;
import viewModel.CarPopUpVM;

public class CarCommandInsert implements CommandInterface {
    private final CarPersistence carPersistence = new CarPersistence();
    private final CarPopUpVM carPopUpVM;

    public CarCommandInsert(CarPopUpVM carPopUpVM) {
        this.carPopUpVM = carPopUpVM;
    }

    @Override
    public boolean execute() {
        Car car = new Car(
                carPopUpVM.getIdTextField(),
                carPopUpVM.getBrandTextField(),
                carPopUpVM.getModelTextField(),
                carPopUpVM.getColorTextField(),
                carPopUpVM.getFuelTextField()
        );
        carPersistence.insertObject(car);
        return false;
    }
}
