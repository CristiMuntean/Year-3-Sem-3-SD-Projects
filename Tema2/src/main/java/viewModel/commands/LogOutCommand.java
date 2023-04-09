package viewModel.commands;

import view.LoginView;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LogOutCommand implements CommandInterface {
    @Override
    public boolean execute() {
        LoginView loginView = new LoginView("Log in");
        loginView.setVisible(true);
        loginView.setDefaultCloseOperation(EXIT_ON_CLOSE);
        return true;
    }
}
