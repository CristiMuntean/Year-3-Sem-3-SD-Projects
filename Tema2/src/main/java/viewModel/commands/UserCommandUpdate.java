package viewModel.commands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.UserPopUpVM;

public class UserCommandUpdate implements CommandInterface {
    private final UserPersistence userPersistence = new UserPersistence();
    private final UserPopUpVM userPopUpVM;

    public UserCommandUpdate(UserPopUpVM userPopUpVM) {
        this.userPopUpVM = userPopUpVM;
    }

    @Override
    public boolean execute() {
        User user = new User(
                userPopUpVM.getIdTextField(),
                userPopUpVM.getUsernameTextField(),
                userPopUpVM.getPasswordTextField(),
                userPopUpVM.getRoleTextField()
        );
        userPersistence.updateUser(user, userPopUpVM.getSelectedUser());
        return false;
    }
}
