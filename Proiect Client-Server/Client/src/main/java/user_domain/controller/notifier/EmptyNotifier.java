package user_domain.controller.notifier;

import user_domain.model.User;

public class EmptyNotifier implements NotifierInterface{
    @Override
    public void notifyUser(User oldUser, User newUser) {

    }
}
