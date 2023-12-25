package user_domain.controller.notifier;

import user_domain.model.User;

public interface NotifierInterface {
    void notifyUser(User oldUser, User newUser);
}
