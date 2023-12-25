package viewModel.commands;

import model.Owner;
import view.OperationsView;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


public class OwnerOperationsCommand implements CommandInterface {
    @Override
    public boolean execute() {
        OperationsView<Owner> operationsView = new OperationsView<>("Owner operations", Owner.class);
        operationsView.setVisible(true);
        operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return true;
    }
}
