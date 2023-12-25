package controller;

import com.mysql.cj.util.StringUtils;
import model.Car;
import persistence.CarPersistence;
import view.CarPopUpView;
import view.OperationsInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.*;

public class CarPopUpController implements ActionListener {
    private static CarPopUpController instance;
    private final CarPersistence carPersistence;
    private OperationsInterface<Car> carOperationsInterface;
    private CarPopUpView carPopUpView;
    private Language language;

    private CarPopUpController(OperationsInterface<Car> carOperationsInterface) {
        carPersistence = new CarPersistence();
        this.carOperationsInterface = carOperationsInterface;
    }

    public static CarPopUpController getControllerInstance(OperationsInterface<Car> carOperationsView, CarPopUpView carPopUpView) {
        if (instance == null) {
            instance = new CarPopUpController(carOperationsView);
        }
        instance.setCarOperationsInterface(carOperationsView);
        instance.setCarPopUpView(carPopUpView);
        instance.language = Language.getLanguageInstance();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isNullOrEmpty(carPopUpView.getIdTextField().getText()))return;
        if(isNullOrEmpty(carPopUpView.getBrandTextField().getText()))return;
        if(isNullOrEmpty(carPopUpView.getModelNameTextField().getText()))return;
        if(isNullOrEmpty(carPopUpView.getColorTextField().getText()))return;
        if(isNullOrEmpty(carPopUpView.getFuelTypeTextField().getText()))return;
        Car car = new Car(
                carPopUpView.getIdTextField().getText(),
                carPopUpView.getBrandTextField().getText(),
                carPopUpView.getModelNameTextField().getText(),
                carPopUpView.getColorTextField().getText(),
                carPopUpView.getFuelTypeTextField().getText()
        );
        if (e.getActionCommand().contains("Add")) {
            insertCar(car);
        } else {
            updateCar(car, carPopUpView.getSelectedCar());
        }
        carPopUpView.dispose();
    }

    public void setCarPopUpView(CarPopUpView carPopUpView) {
        this.carPopUpView = carPopUpView;
    }

    public Language getLanguage() {
        return language;
    }
}
