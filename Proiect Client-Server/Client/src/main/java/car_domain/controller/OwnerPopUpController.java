package car_domain.controller;

import car_domain.model.Owner;
import car_domain.view.OperationsInterface;
import car_domain.view.OwnerPopUpView;
import client.ProxyClient;
import user_domain.view.UserInterface;
import user_domain.view.language.Language;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class OwnerPopUpController implements ActionListener {
    private static OwnerPopUpController instance;
    private OperationsInterface<Owner> ownerOperationsInterface;
    private UserInterface userInterface;
    private OwnerPopUpView ownerPopUpView;
    private Language language;
    private ProxyClient proxyClient;

    private OwnerPopUpController(OperationsInterface<Owner> ownerOperationsInterface) {
        this.ownerOperationsInterface = ownerOperationsInterface;
    }

    public static OwnerPopUpController getControllerInstance(OperationsInterface<Owner> ownerOperationsInterface, OwnerPopUpView ownerPopUpView, ProxyClient client,
                                                             UserInterface userInterface) {
        if (instance == null) {
            instance = new OwnerPopUpController(ownerOperationsInterface);
        }
        instance.setOwnerOperationsInterface(ownerOperationsInterface);
        instance.setOwnerPopUpView(ownerPopUpView);
        instance.setUserInterface(userInterface);
        instance.setProxyClient(client);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public void sendInsertOwnerRequest(Owner owner) {
        proxyClient.insertOwner(owner);
    }

    public void sendUpdateOwnerRequest(Owner newOwner, Owner oldOwner) {
        proxyClient.updateOwner(newOwner, oldOwner);
    }

    public void insertOwner(Owner owner) {
        sendInsertOwnerRequest(owner);
        ownerOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateOwner(Owner owner, Owner selectedOwner) {
        sendUpdateOwnerRequest(owner, selectedOwner);
        ownerOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void setOwnerOperationsInterface(OperationsInterface<Owner> ownerOperationsInterface) {
        this.ownerOperationsInterface = ownerOperationsInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isNullOrEmpty(ownerPopUpView.getCnpTextField().getText())) return;
        if (isNullOrEmpty(ownerPopUpView.getNameTextField().getText())) return;
        if (isNullOrEmpty(ownerPopUpView.getSurnameTextField().getText())) return;
        Owner owner = new Owner(
                ownerPopUpView.getCnpTextField().getText(),
                ownerPopUpView.getNameTextField().getText(),
                ownerPopUpView.getSurnameTextField().getText()
        );

        if (e.getActionCommand().contains("Add")) {
            insertOwner(owner);
        } else {
            updateOwner(owner, ownerPopUpView.getSelectedOwner());
        }
        ownerPopUpView.dispose();
    }

    public void setOwnerPopUpView(OwnerPopUpView ownerPopUpView) {
        this.ownerPopUpView = ownerPopUpView;
    }

    public Language getLanguage() {
        return language;
    }

    public void setProxyClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}
