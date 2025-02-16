import javax.swing.*;
import java.awt.*;

/**
 * Panel for the exercise tab.
 * Used for management of exercises.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */

public class ExercisePanel extends JPanel {
  public ExercisePanel() {
    setLayout(new BorderLayout());
    add(new JLabel("Exercise Management"), BorderLayout.CENTER);
  }
}
