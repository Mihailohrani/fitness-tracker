import java.awt.*;
import javax.swing.*;


public class FitnessTrackerGUI extends JFrame {

  public FitnessTrackerGUI() {
    super("Fitness Tracker");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Workouts", new WorkoutPanel());
    tabbedPane.addTab("Workout Plan", new WorkoutPlanPannel());
    tabbedPane.addTab("Exercises", new ExercisePanel());

    add(tabbedPane, BorderLayout.CENTER);

  }
}
