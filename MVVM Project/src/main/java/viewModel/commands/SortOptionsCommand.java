package viewModel.commands;

import viewModel.AbstractEmployeeVM;
import viewModel.EmployeeVM;
import viewModel.ManagerVM;

public class SortOptionsCommand implements CommandInterface {

    private final AbstractEmployeeVM abstractEmployeeVM;

    public SortOptionsCommand(AbstractEmployeeVM abstractEmployeeVM) {
        this.abstractEmployeeVM = abstractEmployeeVM;
    }

    @Override
    public boolean execute() {
        if (abstractEmployeeVM instanceof EmployeeVM employeeVM) {
            if (employeeVM.getSortBrandField() && employeeVM.getSortFuelField())
                employeeVM.setSortOptions("Both");
            else if (employeeVM.getSortBrandField())
                employeeVM.setSortOptions("Brand");
            else if (employeeVM.getSortFuelField())
                employeeVM.setSortOptions("Fuel");
            else employeeVM.setSortOptions("None");
        } else {
            ManagerVM managerVM = (ManagerVM) abstractEmployeeVM;
            if (managerVM.getSortBrandField() && managerVM.getSortFuelField())
                managerVM.setSortOptions("Both");
            else if (managerVM.getSortBrandField())
                managerVM.setSortOptions("Brand");
            else if (managerVM.getSortFuelField())
                managerVM.setSortOptions("Fuel");
            else managerVM.setSortOptions("None");
        }
        return true;
    }
}
