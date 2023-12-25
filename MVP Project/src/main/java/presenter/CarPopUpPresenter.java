package presenter;

import model.Car;
import persistence.CarPersistence;
import view.OperationsInterface;

public class CarPopUpPresenter {
    private static CarPopUpPresenter instance;
    private final CarPersistence carPersistence;
    private OperationsInterface<Car> carOperationsInterface;

    private CarPopUpPresenter(OperationsInterface<Car> carOperationsInterface) {
        carPersistence = new CarPersistence();
        this.carOperationsInterface = carOperationsInterface;
    }

    public static CarPopUpPresenter getPresenterInstance(OperationsInterface<Car> carOperationsView) {
        if (instance == null) {
            instance = new CarPopUpPresenter(carOperationsView);
            instance.setCarOperationsInterface(carOperationsView);
        }
        return instance;
    }

    public void insertCar(Car car) {
        carPersistence.insertObject(car);
        carOperationsInterface.refreshPanel();
    }

    public void updateCar(Car car, Car selectedCar) {
        carPersistence.updateCar(car, selectedCar);
        carOperationsInterface.refreshPanel();
    }

    public void setCarOperationsInterface(OperationsInterface<Car> carOperationsInterface) {
        this.carOperationsInterface = carOperationsInterface;
    }
}
