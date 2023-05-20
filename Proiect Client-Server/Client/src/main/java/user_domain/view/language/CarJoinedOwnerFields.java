package user_domain.view.language;

import java.io.Serializable;

public class CarJoinedOwnerFields implements Serializable {
    private String serviceNumber;
    private String carId;
    private String brand;
    private String modelName;
    private String color;
    private String fuelType;
    private String cnp;
    private String carOwner;

    public CarJoinedOwnerFields(String serviceNumber, String carId, String brand, String modelName, String color, String fuelType, String cnp, String carOwner) {
        this.serviceNumber = serviceNumber;
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.color = color;
        this.fuelType = fuelType;
        this.cnp = cnp;
        this.carOwner = carOwner;
    }

    public CarJoinedOwnerFields() {
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }
}
