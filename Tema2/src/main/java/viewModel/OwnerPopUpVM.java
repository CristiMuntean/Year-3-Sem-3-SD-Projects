package viewModel;

import model.Owner;
import model.persistence.OwnerPersistence;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.commands.CommandInterface;
import viewModel.commands.OwnerCommandInsert;
import viewModel.commands.OwnerCommandUpdate;

public class OwnerPopUpVM {
    private static OwnerPopUpVM instance;
    private final OwnerPersistence ownerPersistence;
    private Owner selectedOwner;
    private final Property<String> cnpTextField;
    private final Property<String> nameTextField;
    private final Property<String> surnameTextField;
    private final CommandInterface ownerCommandInsert;
    private final CommandInterface ownerCommandUpdate;

    private OwnerPopUpVM() {
        this.ownerPersistence = new OwnerPersistence();
        cnpTextField = PropertyFactory.createProperty("cnp", this, String.class);
        nameTextField = PropertyFactory.createProperty("name", this, String.class);
        surnameTextField = PropertyFactory.createProperty("surname", this, String.class);

        ownerCommandInsert = new OwnerCommandInsert(this);
        ownerCommandUpdate = new OwnerCommandUpdate(this);
    }

    public static OwnerPopUpVM getVMInstance() {
        if (instance == null) {
            instance = new OwnerPopUpVM();
        }
        return instance;
    }

    public void insertOwner(Owner owner) {
        ownerPersistence.insertObject(owner);
    }

    public void updateOwner(Owner owner, Owner selectedOwner) {
        ownerPersistence.updateOwner(owner, selectedOwner);
    }

    public String getCnpTextField() {
        return cnpTextField.get();
    }

    public void setCnpTextField(String cnpTextField) {
        this.cnpTextField.set(cnpTextField);
    }

    public String getNameTextField() {
        return nameTextField.get();
    }

    public void setNameTextField(String nameTextField) {
        this.nameTextField.set(nameTextField);
    }

    public String getSurnameTextField() {
        return surnameTextField.get();
    }

    public void setSurnameTextField(String surnameTextField) {
        this.surnameTextField.set(surnameTextField);
    }

    public CommandInterface getOwnerCommandInsert() {
        return ownerCommandInsert;
    }

    public CommandInterface getOwnerCommandUpdate() {
        return ownerCommandUpdate;
    }

    public Owner getSelectedOwner() {
        return selectedOwner;
    }

    public void setSelectedOwner(Owner selectedOwner) {
        this.selectedOwner = selectedOwner;
    }
}
