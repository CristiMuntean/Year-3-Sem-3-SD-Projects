import model.User;
import model.persistence.DatabaseFactory;
import model.persistence.UserPersistence;
import view.LoginView;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    public static void main(String[] args) {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        if (!databaseFactory.initializeDatabase())
            System.out.println("Could not initialize database: one or more tables already exist");
        UserPersistence userPersistence = new UserPersistence();
        userPersistence.insertObject(new User("1", "admin", "admin", "admin"));
        LoginView loginView = new LoginView("Login");
        loginView.setVisible(true);
        loginView.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
