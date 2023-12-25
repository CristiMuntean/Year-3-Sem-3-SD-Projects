package car_domain.model;

import java.io.Serializable;

public class ServiceLog implements Serializable {
    private String serviceNumber;
    private String ownerCnp;
    private String carId;

    public ServiceLog() {
    }

    public ServiceLog(String serviceNumber, String ownerCnp, String carId) {
        this.serviceNumber = serviceNumber;
        this.ownerCnp = ownerCnp;
        this.carId = carId;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ServiceLog log)) return false;
        return this.serviceNumber.equals(log.getServiceNumber())
                && this.ownerCnp.equals(log.getOwnerCnp())
                && this.carId.equals(log.getCarId());
    }
}
