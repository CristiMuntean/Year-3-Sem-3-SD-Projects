package user_domain.controller.notifier;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import user_domain.model.User;

public class WhatsAppNotifier extends NotifierDecorator {
    public WhatsAppNotifier(NotifierInterface notifier) {
        super(notifier);
        System.out.println("Adding whatsapp notifier functionality");
    }

    public void notifyUser(User oldUser, User newUser) {
        notifier.notifyUser(oldUser, newUser);
        notifyUserViaWhatsApp(oldUser, newUser);
    }

    private void notifyUserViaWhatsApp(User user, User changedUser) {
        String ACCOUNT_SID = "AC35ca10ffcd4385b85ce5d3ee40237544";
        String AUTH_TOKEN = "81817ef1f50e65c83bca6b87d40c1a65";
        String messageBody = "Your account details have been changed. Below will be provided the changes that have been made.\n" +
                "Username from: " + user.getUsername() + ", to: " + changedUser.getUsername() + "\n" +
                "Password from: " + user.getPassword() + ", to: " + changedUser.getPassword() + "\n" +
                "Role from: " + user.getRole() + ", to: " + changedUser.getRole() + "\n" +
                "Email from: " + user.getEmail() + ", to: " + changedUser.getEmail() + "\n" +
                "Phone number from: " + user.getPhoneNumber() + ", to: " + changedUser.getPhoneNumber();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String phoneNumber = "whatsapp:+40" + user.getPhoneNumber().substring(1);
        com.twilio.rest.api.v2010.account.Message message = new MessageCreator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("whatsapp:+14155238886"),
                messageBody).create();
        System.out.println("WhatsApp message sent");
    }
}
