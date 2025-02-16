import javax.swing.*;
import java.awt.*;

/**
 * Panel for the workout plan tab.
 * Used for management of workout plans.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */

public class WorkoutPlanPannel extends JPanel {
  public WorkoutPlanPannel() {
    setLayout(new BorderLayout());
    add(new JLabel("Workout Planner"), BorderLayout.CENTER);
  }
}
