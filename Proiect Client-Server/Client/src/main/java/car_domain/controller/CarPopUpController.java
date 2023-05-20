package car_domain.controller;

import car_domain.model.Car;
import car_domain.view.CarPopUpView;
import car_domain.view.OperationsInterface;
import client.ProxyClient;
import user_domain.view.UserInterface;
import user_domain.view.language.Language;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class CarPopUpController implements ActionListener {
    private static CarPopUpController instance;
    private OperationsInterface<Car> carOperationsInterface;
    private CarPopUpView carPopUpView;
    private Language language;
    private ProxyClient proxyClient;
    private UserInterface userInterface;

    private CarPopUpController(OperationsInterface<Car> carOperationsInterface) {
        this.carOperationsInterface = carOperationsInterface;
    }

    public static CarPopUpController getControllerInstance(OperationsInterface<Car> carOperationsView,
                                                           CarPopUpView carPopUpView, ProxyClient client,
                                                           UserInterface userInterface) {
        if (instance == null) {
            instance = new CarPopUpController(carOperationsView);
        }
        instance.setCarOperationsInterface(carOperationsView);
        instance.setCarPopUpView(carPopUpView);
        instance.setProxyClient(client);
        instance.setUserInterface(userInterface);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public void sendInsertCarRequest(Car car) {
        proxyClient.insertCar(car);
    }

    public void sendUpdateCarRequest(Car newCar, Car oldCar) {
        proxyClient.updateCar(newCar, oldCar);
    }

    public void insertCar(Car car) {
        sendInsertCarRequest(car);
        carOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateCar(Car car, Car selectedCar) {
        sendUpdateCarRequest(car, selectedCar);
        carOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void setCarOperationsInterface(OperationsInterface<Car> carOperationsInterface) {
        this.carOperationsInterface = carOperationsInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isNullOrEmpty(carPopUpView.getIdTextField().getText())) return;
        if (isNullOrEmpty(carPopUpView.getBrandTextField().getText())) return;
        if (isNullOrEmpty(carPopUpView.getModelNameTextField().getText())) return;
        if (isNullOrEmpty(carPopUpView.getColorTextField().getText())) return;
        if (isNullOrEmpty(carPopUpView.getFuelTypeTextField().getText())) return;
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

    public void setProxyClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}
