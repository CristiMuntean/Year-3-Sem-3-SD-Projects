package controller;

import model.Owner;
import persistence.OwnerPersistence;
import view.OperationsInterface;
import view.OwnerPopUpView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class OwnerPopUpController implements ActionListener {
    private static OwnerPopUpController instance;
    private final OwnerPersistence ownerPersistence;
    private OperationsInterface<Owner> ownerOperationsInterface;
    private OwnerPopUpView ownerPopUpView;
    private Language language;

    private OwnerPopUpController(OperationsInterface<Owner> ownerOperationsInterface) {
        this.ownerPersistence = new OwnerPersistence();
        this.ownerOperationsInterface = ownerOperationsInterface;
    }

    public static OwnerPopUpController getControllerInstance(OperationsInterface<Owner> ownerOperationsInterface, OwnerPopUpView ownerPopUpView) {
        if (instance == null) {
            instance = new OwnerPopUpController(ownerOperationsInterface);
        }
        instance.setOwnerOperationsInterface(ownerOperationsInterface);
        instance.setOwnerPopUpView(ownerPopUpView);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public void insertOwner(Owner owner) {
        ownerPersistence.insertObject(owner);
        ownerOperationsInterface.refreshPanel();
    }

    public void updateOwner(Owner owner, Owner selectedOwner) {
        ownerPersistence.updateOwner(owner, selectedOwner);
        ownerOperationsInterface.refreshPanel();
    }

    public void setOwnerOperationsInterface(OperationsInterface<Owner> ownerOperationsInterface) {
        this.ownerOperationsInterface = ownerOperationsInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isNullOrEmpty(ownerPopUpView.getCnpTextField().getText())) return;
        if(isNullOrEmpty(ownerPopUpView.getNameTextField().getText())) return;
        if(isNullOrEmpty(ownerPopUpView.getSurnameTextField().getText())) return;
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
}
