package user_domain.view;

import javax.swing.*;
import java.util.List;

public interface EmployeeInterface extends UserInterface {
    List<JCheckBox> getSortCheckBoxes();
    void clearPanel();
    void reprepareGui(JTable table, String filterOptions);
    List<JComboBox<String>> getFilterComboBoxes();
    JComboBox<String> getSearchComboBox();
    JComboBox<String> getSaveComboBox();
}
