import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Panel for the workout tab.
 * Users can add, view, and remove workouts.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.18
 */
public class WorkoutPanel extends JPanel implements ActionListener {
  private final JButton buttonAddWorkout, buttonRemoveWorkout;
  private final DefaultListModel<String> workoutListModel;
  private final JList<String> workoutList;
  private final List<Workout> workouts;
  private final OutputHandler outputHandler;

  public WorkoutPanel() {
    setLayout(null);

    JLabel titleLabel = new JLabel("Workout Management");
    titleLabel.setBounds(300, 10, 200, 30);
    add(titleLabel);

    buttonAddWorkout = new JButton("Add Workout");
    buttonAddWorkout.setBounds(50, 50, 150, 30);
    buttonAddWorkout.addActionListener(this);
    add(buttonAddWorkout);

    buttonRemoveWorkout = new JButton("Remove Workout");
    buttonRemoveWorkout.setBounds(220, 50, 150, 30);
    buttonRemoveWorkout.addActionListener(this);
    add(buttonRemoveWorkout);

    workoutListModel = new DefaultListModel<>();
    workoutList = new JList<>(workoutListModel);
    JScrollPane listScrollPane = new JScrollPane(workoutList);
    listScrollPane.setBounds(50, 100, 300, 200);
    add(listScrollPane);

    JTextArea outputArea = new JTextArea();
    outputArea.setEditable(false);
    JScrollPane outputScrollPane = new JScrollPane(outputArea);
    outputScrollPane.setBounds(50, 320, 500, 100);
    add(outputScrollPane);

    outputHandler = new OutputHandler(outputArea);
    workouts = new ArrayList<>();
  }

  /**
   * Handles button clicks.
   *
   * @param e the action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonAddWorkout) {
      showAddWorkoutDialog();
    } else if (e.getSource() == buttonRemoveWorkout) {
      removeSelectedWorkout();
    }
  }

  /**
   * Shows a dialog for adding a workout.
   */
  private void showAddWorkoutDialog() {
    JTextField nameField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField dateField = new JTextField("yyyy-MM-dd");

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));
    panel.add(new JLabel("Workout Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Description:"));
    panel.add(descriptionField);
    panel.add(new JLabel("Date (yyyy-MM-dd):"));
    panel.add(dateField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add Workout", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      processWorkoutInput(nameField, descriptionField, dateField);
    }
  }

  /**
   * Processes input from the add workout dialog.
   *
   * @param nameField the name field
   * @param descriptionField the description field
   * @param dateField the date field
   */
  private void processWorkoutInput(JTextField nameField, JTextField descriptionField, JTextField dateField) {
    try {
      String name = nameField.getText().trim();
      String description = descriptionField.getText().trim();
      Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());

      if (name.isEmpty() || description.isEmpty()) {
        throw new IllegalArgumentException("Fields cannot be empty.");
      }

      Workout workout = new Workout(name, description, date);
      workouts.add(workout);
      workoutListModel.addElement(workout.getName() + " - " + workout.getDate());
      outputHandler.print("Added Workout: " + workout);

    } catch (ParseException e) {
      JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Removes the selected workout from the list.
   */
  private void removeSelectedWorkout() {
    int selectedIndex = workoutList.getSelectedIndex();
    if (selectedIndex != -1) {
      workouts.remove(selectedIndex);
      workoutListModel.remove(selectedIndex);
      outputHandler.print("Removed workout.");
    } else {
      JOptionPane.showMessageDialog(this, "Please select a workout to remove.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
