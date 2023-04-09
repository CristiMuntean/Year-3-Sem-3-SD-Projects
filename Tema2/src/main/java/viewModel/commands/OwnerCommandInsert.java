package viewModel.commands;

import model.Owner;
import model.persistence.OwnerPersistence;
import viewModel.OwnerPopUpVM;

public class OwnerCommandInsert implements CommandInterface {
    private static final OwnerPersistence ownerPersistence = new OwnerPersistence();
    private final OwnerPopUpVM ownerPopUpVM;

    public OwnerCommandInsert(OwnerPopUpVM ownerPopUpVM) {
        this.ownerPopUpVM = ownerPopUpVM;
    }

    @Override
    public boolean execute() {
        Owner owner = new Owner(
                ownerPopUpVM.getCnpTextField(),
                ownerPopUpVM.getNameTextField(),
                ownerPopUpVM.getSurnameTextField()
        );
        ownerPersistence.insertObject(owner);
        return true;
    }
}
