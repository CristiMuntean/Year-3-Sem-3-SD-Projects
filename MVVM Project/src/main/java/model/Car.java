package model;

public class Car {
    private String id;
    private String brand;
    private String modelName;
    private String color;
    private String fuelType;

    public Car() {
    }

    public Car(String id, String brand, String modelName, String color, String fuelType) {
        this.id = id;
        this.brand = brand;
        this.modelName = modelName;
        this.color = color;
        this.fuelType = fuelType;
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
        if (brand != null)
            this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (modelName != null)
            this.modelName = modelName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color != null)
            this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        if (fuelType != null)
            this.fuelType = fuelType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Car car)) return false;
        return this.id.equals(car.getId())
                && this.brand.equals(car.getBrand())
                && this.modelName.equals(car.getModelName())
                && this.color.equals(car.getColor())
                && this.fuelType.equals(car.getFuelType());
    }

    @Override
    public String toString() {
        return "id:" + this.id + "," + "brand:" + this.brand + "," + "modelName:" +
                this.modelName + "," + "color:" + this.color + "," + "fuelType:" + this.fuelType;
    }
}
