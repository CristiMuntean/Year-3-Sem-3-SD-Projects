package viewModel.commands;

import model.ServiceLog;
import model.persistence.ServiceLogPersistence;
import viewModel.ServiceLogPopUpVM;

public class ServiceLogCommandUpdate implements CommandInterface {
    private final ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
    private final ServiceLogPopUpVM serviceLogPopUpVM;

    public ServiceLogCommandUpdate(ServiceLogPopUpVM serviceLogPopUpVM) {
        this.serviceLogPopUpVM = serviceLogPopUpVM;
    }

    @Override
    public boolean execute() {
        ServiceLog serviceLog = new ServiceLog(
                serviceLogPopUpVM.getServiceNumberTextField(),
                serviceLogPopUpVM.getCnpTextField(),
                serviceLogPopUpVM.getCarIdTextField()
        );
        serviceLogPersistence.updateLog(serviceLog, serviceLogPopUpVM.getSelectedLog());
        return false;
    }
}
