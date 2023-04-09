package viewModel;

import model.Car;
import model.persistence.CarPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CarCommandInsert;
import viewModel.commands.CarCommandUpdate;
import viewModel.commands.CommandInterface;

public class CarPopUpVM {
    private static CarPopUpVM instance;
    private final CarPersistence carPersistence;
    private Car selectedCar;
    private final Property<String> idTextField;
    private final Property<String> brandTextField;
    private final Property<String> modelTextField;
    private final Property<String> colorTextField;
    private final Property<String> fuelTextField;
    private final CommandInterface carCommandInsert;
    private final CommandInterface carCommandUpdate;

    private CarPopUpVM() {
        carPersistence = new CarPersistence();
        idTextField = PropertyFactory.createProperty("id", this, String.class);
        brandTextField = PropertyFactory.createProperty("brand", this, String.class);
        modelTextField = PropertyFactory.createProperty("modelName", this, String.class);
        colorTextField = PropertyFactory.createProperty("color", this, String.class);
        fuelTextField = PropertyFactory.createProperty("fuel", this, String.class);

        carCommandInsert = new CarCommandInsert(this);
        carCommandUpdate = new CarCommandUpdate(this);
    }

    public static CarPopUpVM getVMInstance() {
        if (instance == null) {
            instance = new CarPopUpVM();
        }
        return instance;
    }


    public void updateCar(Car car, Car selectedCar) {
        carPersistence.updateCar(car, selectedCar);
    }

    public String getIdTextField() {
        return idTextField.get();
    }

    public void setIdTextField(String idTextField) {
        this.idTextField.set(idTextField);
    }

    public String getBrandTextField() {
        return brandTextField.get();
    }

    public void setBrandTextField(String brandTextField) {
        this.brandTextField.set(brandTextField);
    }

    public String getModelTextField() {
        return modelTextField.get();
    }

    public void setModelTextField(String modelTextField) {
        this.modelTextField.set(modelTextField);
    }

    public String getColorTextField() {
        return colorTextField.get();
    }

    public void setColorTextField(String colorTextField) {
        this.colorTextField.set(colorTextField);
    }

    public String getFuelTextField() {
        return fuelTextField.get();
    }

    public void setFuelTextField(String fuelTextField) {
        this.fuelTextField.set(fuelTextField);
    }

    public CommandInterface getCarCommandInsert() {
        return carCommandInsert;
    }

    public CommandInterface getCarCommandUpdate() {
        return carCommandUpdate;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
}
