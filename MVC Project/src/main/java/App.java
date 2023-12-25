import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import controller.LoginController;
import model.User;
import persistence.DatabaseFactory;
import persistence.UserPersistence;

import java.lang.reflect.Member;


public class App {
    public static void main(String[] args) {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        if (!databaseFactory.initializeDatabase())
            System.out.println("Could not initialize database: one or more tables already exist");
        UserPersistence userPersistence = new UserPersistence();
        userPersistence.insertObject(new User("1", "admin", "admin", "admin","cristimuntean17@gmail.com","0730850803"));
        LoginController loginController = new LoginController();

    }
}
