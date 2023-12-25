package controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import model.User;
import persistence.UserPersistence;
import view.OperationsInterface;
import view.UserInterface;
import view.UserPopUpView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class UserPopUpController implements ActionListener {
    private static UserPopUpController instance;
    private final UserPersistence userPersistence;
    private OperationsInterface<User> userOperationsInterface;
    private UserInterface userInterface;
    private UserPopUpView userPopUpView;
    private Language language;

    private UserPopUpController(OperationsInterface<User> userOperationsInterface, UserInterface userInterface) {
        this.userPersistence = new UserPersistence();
        this.userOperationsInterface = userOperationsInterface;
        this.userInterface = userInterface;
    }

    public static UserPopUpController getControllerInterface(OperationsInterface<User> userOperationsInterface, UserInterface employeeInterface, UserPopUpView userPopUpView) {
        if (instance == null) {
            instance = new UserPopUpController(userOperationsInterface, employeeInterface);
        }
        instance.setUserOperationsInterface(userOperationsInterface);
        instance.setUserInterface(employeeInterface);
        instance.setUserPopUpView(userPopUpView);
        instance.language = Language.getLanguageInstance();
        return instance;
    }

    public void insertUser(User user) {
        userPersistence.insertObject(user);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }

    public void updateUser(User user, User selectedUser) {
        userPersistence.updateUser(user, selectedUser);
        userOperationsInterface.refreshPanel();
        userInterface.refreshPanel();
    }


    public void setUserOperationsInterface(OperationsInterface<User> userOperationsInterface) {
        this.userOperationsInterface = userOperationsInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isNullOrEmpty(userPopUpView.getIdTextField().getText()))return;
        if(isNullOrEmpty(userPopUpView.getUsernameTextField().getText()))return;
        if(isNullOrEmpty(userPopUpView.getPasswordTextField().getText()))return;
        if(isNullOrEmpty(userPopUpView.getRoleTextField().getText()))return;
        User user = new User(
                userPopUpView.getIdTextField().getText(),
                userPopUpView.getUsernameTextField().getText(),
                userPopUpView.getPasswordTextField().getText(),
                userPopUpView.getRoleTextField().getText(),
                userPopUpView.getEmailTextField().getText(),
                userPopUpView.getPhoneNumberTextField().getText()
        );

         if (e.getActionCommand().contains("Add")) {
            insertUser(user);
        } else {
             User selectedUser = userPopUpView.getSelectedUser();
             updateUser(user, selectedUser);
             notifyUserViaEmail(selectedUser, user);
             notifyUserViaWhatsApp(selectedUser, user);
        }
        userPopUpView.dispose();
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

    public Language getLanguage() {
        return language;
    }

    public void setUserPopUpView(UserPopUpView userPopUpView) {
        this.userPopUpView = userPopUpView;
    }
}
