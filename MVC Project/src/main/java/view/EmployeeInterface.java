package view;

import javax.swing.*;
import java.util.List;

public interface EmployeeInterface extends UserInterface {
    List<JCheckBox> getSortCheckBoxes();
    void clearPanel();
    void reprepareGui();
    List<JComboBox<String>> getFilterComboBoxes();
    JComboBox<String> getSearchComboBox();
    JComboBox<String> getSaveComboBox();
}
