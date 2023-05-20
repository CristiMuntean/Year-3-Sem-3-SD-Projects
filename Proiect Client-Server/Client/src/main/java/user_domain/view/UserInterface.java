package user_domain.view;

import javax.swing.*;

public interface UserInterface {
    void refreshPanel(JTable carsWithOwnersTable, String filterOptions);
    void disposeWindow();

    void refreshPanel();
}
