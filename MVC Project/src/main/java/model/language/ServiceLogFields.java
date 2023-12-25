package model.language;

public class ServiceLogFields {
    private String serviceNumber;
    private String ownerCnp;
    private String carId;

    public ServiceLogFields(String serviceNumber, String ownerCnp, String carId) {
        this.serviceNumber = serviceNumber;
        this.ownerCnp = ownerCnp;
        this.carId = carId;
    }

    public ServiceLogFields() {
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getOwnerCnp() {
        return ownerCnp;
    }

    public void setOwnerCnp(String ownerCnp) {
        this.ownerCnp = ownerCnp;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
