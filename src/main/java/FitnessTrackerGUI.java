import java.awt.*;
import javax.swing.*;

/**
 * The main GUI class for the Fitness Tracker application.
 * This class is responsible for creating the main window and adding the different tabs to it.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.23
 */

public class FitnessTrackerGUI extends JFrame {

  public FitnessTrackerGUI() {
    super("Fitness Tracker");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);

    ExercisePanel exercisePanel = new ExercisePanel(null);
    WorkoutPanel workoutPanel = new WorkoutPanel(exercisePanel);
    exercisePanel = new ExercisePanel(workoutPanel);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Workouts", workoutPanel);
    tabbedPane.addTab("Workout Plan", new WorkoutPlanPanel());
    tabbedPane.addTab("Exercises", exercisePanel);

    add(tabbedPane, BorderLayout.CENTER);
  }

}
