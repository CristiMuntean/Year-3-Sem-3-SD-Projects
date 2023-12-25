package controller;

import model.ServiceLog;
import persistence.ServiceLogPersistence;
import view.OperationsInterface;
import view.ServiceLogPopUpView;
import view.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class ServiceLogPopUpController implements ActionListener {
    private static ServiceLogPopUpController instance;
    private final ServiceLogPersistence serviceLogPersistence;
    private OperationsInterface<ServiceLog> serviceLogOperationsInterface;
    private UserInterface userInterface;
    private ServiceLogPopUpView serviceLogPopUpView;
    private Language language;

    private ServiceLogPopUpController(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface userInterface) {
        this.serviceLogPersistence = new ServiceLogPersistence();
        this.serviceLogOperationsInterface = serviceLogOperationsInterface;
        this.userInterface = userInterface;
    }

    public static ServiceLogPopUpController getControllerInterface(OperationsInterface<ServiceLog> serviceLogOperationsInterface, UserInterface employeeInterface, ServiceLogPopUpView serviceLogPopUpView) {
        if (instance == null) {
            instance = new ServiceLogPopUpController(serviceLogOperationsInterface, employeeInterface);
        }
        instance.setServiceLogOperationsInterface(serviceLogOperationsInterface);
        instance.setUserInterface(employeeInterface);
        instance.setServiceLogPopUpView(serviceLogPopUpView);
        instance.language = Language.getLanguageInstance();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isNullOrEmpty(serviceLogPopUpView.getServiceNumberTextField().getText()))return;
        if(isNullOrEmpty(serviceLogPopUpView.getOwnerCnpTextField().getText()))return;
        if(isNullOrEmpty(serviceLogPopUpView.getCarIdTextField().getText()))return;
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
}
