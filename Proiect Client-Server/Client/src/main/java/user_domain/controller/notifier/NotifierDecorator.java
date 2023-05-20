package user_domain.controller.notifier;

import user_domain.model.User;

public abstract class NotifierDecorator implements NotifierInterface {
    protected NotifierInterface notifier;

    public NotifierDecorator(NotifierInterface notifier) {
        this.notifier = notifier;
    }

    public void notifyUser(User oldUser, User newUser) {
        notifier.notifyUser(oldUser, newUser);
    }
}
