package viewModel.commands;

import viewModel.AbstractEmployeeVM;
import viewModel.EmployeeVM;
import viewModel.ManagerVM;

public class FilteringOptionsCommand implements CommandInterface {

    private final AbstractEmployeeVM abstractEmployeeVM;

    public FilteringOptionsCommand(AbstractEmployeeVM abstractEmployeeVM) {
        this.abstractEmployeeVM = abstractEmployeeVM;
    }


    @Override
    public boolean execute() {
        StringBuilder filterOptionsBuilder = new StringBuilder();
        if (abstractEmployeeVM instanceof EmployeeVM employeeVM) {
            if (!employeeVM.getSelectedFilterOwnerField().equals("Owner"))
                filterOptionsBuilder.append("Owner:").append(employeeVM.getSelectedFilterOwnerField().replace(" ", "_")).append(" ");
            if (!employeeVM.getSelectedFilterBrandField().equals("Car Brand"))
                filterOptionsBuilder.append("CarBrand:").append(employeeVM.getSelectedFilterBrandField()).append(" ");
            if (!employeeVM.getSelectedFilterFuelField().equals("Fuel Type"))
                filterOptionsBuilder.append("FuelType:").append(employeeVM.getSelectedFilterFuelField()).append(" ");
            if (!employeeVM.getSelectedFilterColorField().equals("Color"))
                filterOptionsBuilder.append("Color:").append(employeeVM.getSelectedFilterColorField()).append(" ");
        } else {
            ManagerVM managerVM = (ManagerVM) abstractEmployeeVM;
            if (!managerVM.getSelectedFilterOwnerField().equals("Owner"))
                filterOptionsBuilder.append("Owner:").append(managerVM.getSelectedFilterOwnerField().replace(" ", "_")).append(" ");
            if (!managerVM.getSelectedFilterBrandField().equals("Car Brand"))
                filterOptionsBuilder.append("CarBrand:").append(managerVM.getSelectedFilterBrandField()).append(" ");
            if (!managerVM.getSelectedFilterFuelField().equals("Fuel Type"))
                filterOptionsBuilder.append("FuelType:").append(managerVM.getSelectedFilterFuelField()).append(" ");
            if (!managerVM.getSelectedFilterColorField().equals("Color"))
                filterOptionsBuilder.append("Color:").append(managerVM.getSelectedFilterColorField()).append(" ");
        }
        if (filterOptionsBuilder.toString().equals(""))
            abstractEmployeeVM.setFilterOptions("None");
        else abstractEmployeeVM.setFilterOptions(filterOptionsBuilder.toString());
        return true;
    }
}
