package viewModel.commands;

import viewModel.AdminVM;

public class FilterCommand implements CommandInterface {

    private final AdminVM adminVM;

    public FilterCommand(AdminVM adminVM) {
        this.adminVM = adminVM;
    }

    @Override
    public boolean execute() {
        this.adminVM.setFilterOptions(this.adminVM.getSelectedFilter());
        return true;
    }
}
