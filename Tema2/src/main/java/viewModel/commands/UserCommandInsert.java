package viewModel.commands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.UserPopUpVM;

public class UserCommandInsert implements CommandInterface {
    private final UserPersistence userPersistence = new UserPersistence();
    private final UserPopUpVM userPopUpVM;

    public UserCommandInsert(UserPopUpVM userPopUpVM) {
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
        userPersistence.insertObject(user);
        return false;
    }
}
