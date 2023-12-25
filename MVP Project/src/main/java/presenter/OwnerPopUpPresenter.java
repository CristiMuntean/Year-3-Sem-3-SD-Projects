package presenter;

import model.Owner;
import persistence.OwnerPersistence;
import view.OperationsInterface;

public class OwnerPopUpPresenter {
    private static OwnerPopUpPresenter instance;
    private final OwnerPersistence ownerPersistence;
    private OperationsInterface<Owner> ownerOperationsInterface;

    private OwnerPopUpPresenter(OperationsInterface<Owner> ownerOperationsInterface) {
        this.ownerPersistence = new OwnerPersistence();
        this.ownerOperationsInterface = ownerOperationsInterface;
    }

    public static OwnerPopUpPresenter getPresenterInstance(OperationsInterface<Owner> ownerOperationsInterface) {
        if (instance == null) {
            instance = new OwnerPopUpPresenter(ownerOperationsInterface);
            instance.setOwnerOperationsInterface(ownerOperationsInterface);
        }
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
}
