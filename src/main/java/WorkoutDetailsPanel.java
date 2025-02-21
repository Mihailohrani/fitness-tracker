import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code WorkoutDetailsPanel} class represents the panel that displays and manages
 * workouts for a selected day. It allows users to view, add, and manage workouts.
 *
 * This panel is dynamically updated when a day is selected from the {@code WorkoutPlanPanel}.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.21
 */
public class WorkoutDetailsPanel extends JPanel implements ActionListener {
  private final WorkoutPlan workoutPlan;
  private final JTextArea workoutDisplay;
  private LocalDate selectedDate;

  /**
   * Constructs a {@code WorkoutDetailsPanel} with an associated {@code WorkoutPlan}.
   *
   * @param workoutPlan The workout plan associated with this panel.
   */
  public WorkoutDetailsPanel(WorkoutPlan workoutPlan) {
    this.workoutPlan = workoutPlan;
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding

    JLabel titleLabel = new JLabel("Workout Details", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setOpaque(true);
    titleLabel.setBackground(Color.LIGHT_GRAY);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    add(titleLabel, BorderLayout.NORTH);

    workoutDisplay = new JTextArea();
    workoutDisplay.setEditable(false);
    workoutDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(workoutDisplay);
    add(scrollPane, BorderLayout.CENTER);

    JButton addWorkoutButton = new JButton("Add Workout");
    addWorkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
    addWorkoutButton.setFocusPainted(false);
    addWorkoutButton.addActionListener(this);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(addWorkoutButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the workout display when a day is selected.
   *
   * @param date The selected date for which workouts should be displayed.
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
   * Handles the action event when the "Add Workout" button is clicked.
   * Opens a dialog to allow the user to input a new workout.
   *
   * @param e The action event triggered by the button click.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (selectedDate == null) {
      JOptionPane.showMessageDialog(this, "Please select a day first!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JTextField nameField = new JTextField();
    JTextField descriptionField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
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
   *
   * @param name        The name of the workout.
   * @param description The description of the workout.
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
