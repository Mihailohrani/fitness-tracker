import java.awt.*;
import javax.swing.*;

/**
 * The main GUI class for the Fitness Tracker application.
 * This class is responsible for creating the main window and adding the different tabs to it.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */

public class FitnessTrackerGUI extends JFrame {

  public FitnessTrackerGUI() {
    super("Fitness Tracker");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Workouts", new WorkoutPanel());
    tabbedPane.addTab("Workout Plan", new WorkoutPlanPanel());
    tabbedPane.addTab("Exercises", new ExercisePanel());

    add(tabbedPane, BorderLayout.CENTER);

  }
}
