package viewModel;

import model.ServiceLog;
import model.persistence.ServiceLogPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CommandInterface;
import viewModel.commands.ServiceLogCommandInsert;
import viewModel.commands.ServiceLogCommandUpdate;


public class ServiceLogPopUpVM {
    private static ServiceLogPopUpVM instance;
    private final ServiceLogPersistence serviceLogPersistence;
    private final Property<String> serviceNumberTextField;
    private final Property<String> cnpTextField;
    private final Property<String> carIdTextField;
    private ServiceLog selectedLog;
    private final CommandInterface serviceLogCommandInsert;
    private final CommandInterface serviceLogCommandUpdate;

    private ServiceLogPopUpVM() {
        this.serviceLogPersistence = new ServiceLogPersistence();

        serviceNumberTextField = PropertyFactory.createProperty("serviceNumber", this, String.class);
        cnpTextField = PropertyFactory.createProperty("ownerCnp", this, String.class);
        carIdTextField = PropertyFactory.createProperty("carId", this, String.class);

        serviceLogCommandInsert = new ServiceLogCommandInsert(this);
        serviceLogCommandUpdate = new ServiceLogCommandUpdate(this);
    }

    public static ServiceLogPopUpVM getVMInterface() {
        if (instance == null) {
            instance = new ServiceLogPopUpVM();
        }
        return instance;
    }

    public void insertLog(ServiceLog serviceLog) {
        serviceLogPersistence.insertObject(serviceLog);
    }

    public void updateLog(ServiceLog serviceLog, ServiceLog selectedServiceLog) {
        serviceLogPersistence.updateLog(serviceLog, selectedServiceLog);
    }

    public ServiceLog getSelectedLog() {
        return selectedLog;
    }

    public void setSelectedLog(ServiceLog selectedLog) {
        this.selectedLog = selectedLog;
    }

    public String getServiceNumberTextField() {
        return serviceNumberTextField.get();
    }

    public void setServiceNumberTextField(String serviceNumberTextField) {
        this.serviceNumberTextField.set(serviceNumberTextField);
    }

    public String getCnpTextField() {
        return cnpTextField.get();
    }

    public void setCnpTextField(String cnpTextField) {
        this.cnpTextField.set(cnpTextField);
    }

    public String getCarIdTextField() {
        return carIdTextField.get();
    }

    public void setCarIdTextField(String carIdTextField) {
        this.carIdTextField.set(carIdTextField);
    }

    public CommandInterface getServiceLogCommandInsert() {
        return serviceLogCommandInsert;
    }

    public CommandInterface getServiceLogCommandUpdate() {
        return serviceLogCommandUpdate;
    }
}
