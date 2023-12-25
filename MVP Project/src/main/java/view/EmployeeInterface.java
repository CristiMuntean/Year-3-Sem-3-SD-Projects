package view;

import javax.swing.*;
import java.util.List;

public interface EmployeeInterface extends UserInterface {
    List<JCheckBox> getSortCheckBoxes();
}
