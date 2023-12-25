package user_domain.controller.notifier;

import user_domain.model.User;

public class NotifierFacade {
    public void notifyUserViaAvailableMeans(User oldUser, User newUser) {
        NotifierInterface notifier = new EmptyNotifier();
        if(isEmailValid(oldUser.getEmail())) {
            notifier = new EmailNotifier(notifier);
        }
        if(isPhoneNumberValid(oldUser.getPhoneNumber())) {
            notifier = new WhatsAppNotifier(notifier);
        }
        notifier.notifyUser(oldUser,newUser);
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        String pattern = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        return phoneNumber.matches(pattern);
    }


    private boolean isEmailValid(String email) {
        String pattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        return email.matches(pattern);
    }
}
