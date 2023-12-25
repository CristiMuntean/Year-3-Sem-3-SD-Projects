package user_domain.controller.notifier;

import user_domain.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotifier extends NotifierDecorator{
    public EmailNotifier(NotifierInterface notifier) {
        super(notifier);
        System.out.println("Adding email notifier functionality");
    }

    public void notifyUser(User oldUser, User newUser) {
        notifier.notifyUser(oldUser, newUser);
        notifyUserViaEmail(oldUser, newUser);
    }

    private void notifyUserViaEmail(User user, User changedUser) {
        String to = user.getEmail();
        String from = "noreply@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cristimuntean17@gmail.com","jtyexzojzlkqyujl");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Account details have been changed");
            message.setText("Your account details have been changed. Below will be provided the changes that have been made.\n" +
                    "Username from: " + user.getUsername() + ", to: " + changedUser.getUsername() + "\n" +
                    "Password from: " + user.getPassword() + ", to: " + changedUser.getPassword() + "\n" +
                    "Role from: " + user.getRole() + ", to: " + changedUser.getRole() + "\n" +
                    "Email from: " + user.getEmail() + ", to: " + changedUser.getEmail() + "\n" +
                    "Phone number from: " + user.getPhoneNumber() + ", to: " + changedUser.getPhoneNumber() + "\n"
            );
            Transport.send(message);
            System.out.println("Email sent");
        } catch (MessagingException e) {
            System.out.println("User email address is not a valid email address: " + user.getEmail());
        }
    }
}
