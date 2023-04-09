package viewModel.commands;

import model.ServiceLog;
import view.OperationsView;

import javax.swing.*;


public class ServiceOperationsCommand implements CommandInterface {
    @Override
    public boolean execute() {
        OperationsView<ServiceLog> serviceLogOperationsView = new OperationsView<>("Service operations", ServiceLog.class);
        serviceLogOperationsView.setVisible(true);
        serviceLogOperationsView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return true;
    }
}
