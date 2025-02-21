import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * A panel that displays and manages workouts for a selected day.
 * Shows existing workouts and allows adding new ones.
 *
 * @author Mihailo
 * @version 2025.02.21
 */
public class WorkoutDetailsPanel extends JPanel implements ActionListener {
  private final WorkoutPlan workoutPlan;
  private final JTextArea workoutDisplay;
  private final JButton addWorkoutButton;
  private LocalDate selectedDate;

  /**
   * Creates a new WorkoutDetailsPanel.
   *
   * @param workoutPlan the workout plan to display
   */
  public WorkoutDetailsPanel(WorkoutPlan workoutPlan) {
    this.workoutPlan = workoutPlan;
    setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("Workout Details", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    add(titleLabel, BorderLayout.NORTH);

    workoutDisplay = new JTextArea();
    workoutDisplay.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(workoutDisplay);
    add(scrollPane, BorderLayout.CENTER);

    addWorkoutButton = new JButton("Add Workout");
    addWorkoutButton.addActionListener(this);
    add(addWorkoutButton, BorderLayout.SOUTH);
  }

  /**
   * Updates the workout display when a day is selected.
   */
  public void updateWorkoutDetails(LocalDate date) {
    this.selectedDate = date;
    List<Workout> workouts = workoutPlan.getWorkoutsOnDate(date);

    workoutDisplay.setText("Workouts for " + date + ":\n");
    if (workouts.isEmpty()) {
      workoutDisplay.append("No workouts yet.\n");
    } else {
      for (Workout w : workouts) {
        workoutDisplay.append("- " + w.getName() + "\n");
      }
    }
  }

  /**
   * Opens a dialog to add a workout.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (selectedDate == null) {
      JOptionPane.showMessageDialog(this, "Please select a day first!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JTextField nameField = new JTextField();
    JTextField descriptionField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(2, 2));
    panel.add(new JLabel("Workout Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Description:"));
    panel.add(descriptionField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add Workout", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      addWorkout(nameField.getText().trim(), descriptionField.getText().trim());
    }
  }

  /**
   * Adds a workout to the selected date and updates the display.
   */
  private void addWorkout(String name, String description) {
    if (name.isEmpty() || description.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Workout workout = new Workout(name, description, java.sql.Date.valueOf(selectedDate));
    workoutPlan.addWorkout(selectedDate, workout);
    updateWorkoutDetails(selectedDate);
  }
}
