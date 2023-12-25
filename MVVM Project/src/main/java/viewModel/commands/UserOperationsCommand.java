package viewModel.commands;

import model.User;
import view.OperationsView;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class UserOperationsCommand implements CommandInterface {

    @Override
    public boolean execute() {
        OperationsView<User> operationsView = new OperationsView<>("User operations", User.class);
        operationsView.setVisible(true);
        operationsView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return true;
    }
}
