package car_domain.controller;

import car_domain.model.ServiceLog;
import car_domain.view.OperationsInterface;
import car_domain.view.ServiceLogPopUpView;
import client.ProxyClient;
import user_domain.view.UserInterface;
import user_domain.view.language.Language;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class ServiceLogPopUpController implements ActionListener {
    private static ServiceLogPopUpController instance;
    private OperationsInterface<ServiceLog> serviceLogOperationsInterface;
    private UserInterface userInterface;
    private ServiceLogPopUpView serviceLogPopUpView;
    private Language language;
    private ProxyClient proxyClient;

    private ServiceLogPopUpController(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface userInterface) {
        this.serviceLogOperationsInterface = serviceLogOperationsInterface;
        this.userInterface = userInterface;
    }

    public static ServiceLogPopUpController getControllerInterface(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface employeeInterface, ServiceLogPopUpView serviceLogPopUpView, ProxyClient client) {
        if (instance == null) {
            instance = new ServiceLogPopUpController(serviceLogOperationsInterface, employeeInterface);
        }
        instance.setServiceLogOperationsInterface(serviceLogOperationsInterface);
        instance.setUserInterface(employeeInterface);
        instance.setProxyClient(client);
        instance.setServiceLogPopUpView(serviceLogPopUpView);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public void sendInsertLogRequest(ServiceLog serviceLog) {
        proxyClient.insertServiceLog(serviceLog);
    }

    public void sendUpdateLogRequest(ServiceLog newServiceLog, ServiceLog oldServiceLog) {
        proxyClient.updateServiceLog(newServiceLog, oldServiceLog);
    }

    public void insertLog(ServiceLog serviceLog) {
        sendInsertLogRequest(serviceLog);
        serviceLogOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateLog(ServiceLog serviceLog, ServiceLog selectedServiceLog) {
        sendUpdateLogRequest(serviceLog, selectedServiceLog);
        serviceLogOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }


    public void setServiceLogOperationsInterface(OperationsInterface<ServiceLog> serviceLogOperationsInterface) {
        this.serviceLogOperationsInterface = serviceLogOperationsInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isNullOrEmpty(serviceLogPopUpView.getServiceNumberTextField().getText())) return;
        if (isNullOrEmpty(serviceLogPopUpView.getOwnerCnpTextField().getText())) return;
        if (isNullOrEmpty(serviceLogPopUpView.getCarIdTextField().getText())) return;
        ServiceLog serviceLog = new ServiceLog(
                serviceLogPopUpView.getServiceNumberTextField().getText(),
                serviceLogPopUpView.getOwnerCnpTextField().getText(),
                serviceLogPopUpView.getCarIdTextField().getText()
        );

        if (e.getActionCommand().contains("Add")) {
            insertLog(serviceLog);
        } else {
            updateLog(serviceLog, serviceLogPopUpView.getSelectedServiceLog());
        }
        serviceLogPopUpView.dispose();
    }

    public void setServiceLogPopUpView(ServiceLogPopUpView serviceLogPopUpView) {
        this.serviceLogPopUpView = serviceLogPopUpView;
    }

    public Language getLanguage() {
        return language;
    }

    public void setProxyClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }
}
