package presenter;

import model.ServiceLog;
import persistence.ServiceLogPersistence;
import view.OperationsInterface;
import view.UserInterface;

public class ServiceLogPopUpPresenter {
    private static ServiceLogPopUpPresenter instance;
    private final ServiceLogPersistence serviceLogPersistence;
    private OperationsInterface<ServiceLog> serviceLogOperationsInterface;
    private UserInterface userInterface;

    private ServiceLogPopUpPresenter(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface userInterface) {
        this.serviceLogPersistence = new ServiceLogPersistence();
        this.serviceLogOperationsInterface = serviceLogOperationsInterface;
        this.userInterface = userInterface;
    }

    public static ServiceLogPopUpPresenter getPresenterInterface(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface employeeInterface) {
        if (instance == null) {
            instance = new ServiceLogPopUpPresenter(serviceLogOperationsInterface, employeeInterface);
            instance.setServiceLogOperationsInterface(serviceLogOperationsInterface);
            instance.setUserInterface(employeeInterface);
        }
        return instance;
    }

    public void insertLog(ServiceLog serviceLog) {
        serviceLogPersistence.insertObject(serviceLog);
        serviceLogOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateLog(ServiceLog serviceLog, ServiceLog selectedServiceLog) {
        serviceLogPersistence.updateLog(serviceLog, selectedServiceLog);
        serviceLogOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }


    public void setServiceLogOperationsInterface(OperationsInterface<ServiceLog> serviceLogOperationsInterface) {
        this.serviceLogOperationsInterface = serviceLogOperationsInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}
