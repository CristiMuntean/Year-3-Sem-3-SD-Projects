package viewModel.commands;

import model.ServiceLog;
import model.persistence.ServiceLogPersistence;
import viewModel.ServiceLogPopUpVM;

public class ServiceLogCommandInsert implements CommandInterface {
    private final ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
    private final ServiceLogPopUpVM serviceLogPopUpVM;

    public ServiceLogCommandInsert(ServiceLogPopUpVM serviceLogPopUpVM) {
        this.serviceLogPopUpVM = serviceLogPopUpVM;
    }

    @Override
    public boolean execute() {
        ServiceLog serviceLog = new ServiceLog(
                serviceLogPopUpVM.getServiceNumberTextField(),
                serviceLogPopUpVM.getCnpTextField(),
                serviceLogPopUpVM.getCarIdTextField()
        );
        serviceLogPersistence.insertObject(serviceLog);
        return false;
    }
}
