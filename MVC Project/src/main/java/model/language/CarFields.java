package model.language;

public class CarFields {
    private String id;
    private String brand;
    private String modelName;
    private String color;
    private String fuelType;

    public CarFields(String id, String brand, String modelName, String color, String fuelType) {
        this.id = id;
        this.brand = brand;
        this.modelName = modelName;
        this.color = color;
        this.fuelType = fuelType;
    }

    public CarFields() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
