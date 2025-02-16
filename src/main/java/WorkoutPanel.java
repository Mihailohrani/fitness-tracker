import javax.swing.*;
import java.awt.*;

/**
 * Panel for the workout tab.
 * Used for management of workouts.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */

public class WorkoutPanel extends JPanel {
  public WorkoutPanel() {
    setLayout(new BorderLayout());
    add(new JLabel("Workout Management"), BorderLayout.CENTER);
  }
}