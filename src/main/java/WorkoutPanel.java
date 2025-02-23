import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The WorkoutPanel class allows users to manage workouts.
 * Users can create, remove workouts, and assign exercises to them.
 * This panel is linked with ExercisePanel to retrieve exercises dynamically.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.23
 */
public class WorkoutPanel extends JPanel implements ActionListener {
  private final JButton buttonAddWorkout, buttonRemoveWorkout, buttonAddExerciseToWorkout;
  private final DefaultListModel<String> workoutListModel;
  private final JList<String> workoutList;
  private final OutputHandler outputHandler;
  private final ExercisePanel exercisePanel;
  private final JComboBox<String> exerciseDropdown;

  /**
   * Constructs a WorkoutPanel that allows users to manage workouts.
   *
   * @param exercisePanel The associated ExercisePanel that provides a list of exercises.
   */
  public WorkoutPanel(ExercisePanel exercisePanel) {
    this.exercisePanel = exercisePanel;
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonAddWorkout = new JButton("Add Workout");
    buttonRemoveWorkout = new JButton("Remove Workout");
    buttonAddExerciseToWorkout = new JButton("Add Exercise to Workout");

    buttonPanel.add(buttonAddWorkout);
    buttonPanel.add(buttonRemoveWorkout);
    buttonPanel.add(buttonAddExerciseToWorkout);

    add(buttonPanel, BorderLayout.NORTH);

    workoutListModel = new DefaultListModel<>();
    workoutList = new JList<>(workoutListModel);
    add(new JScrollPane(workoutList), BorderLayout.CENTER);

    buttonAddWorkout.addActionListener(this);
    buttonRemoveWorkout.addActionListener(this);
    buttonAddExerciseToWorkout.addActionListener(this);

    JTextArea outputArea = new JTextArea();
    outputArea.setEditable(false);
    add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    outputHandler = new OutputHandler(outputArea);

    exerciseDropdown = new JComboBox<>();
    add(exerciseDropdown, BorderLayout.SOUTH);

    loadWorkouts();
    updateExerciseDropdown();
  }

  /**
   * Updates the dropdown list of exercises available for selection.
   */
  public void updateExerciseDropdown() {
    exerciseDropdown.removeAllItems();
    List<Exercise> exercises = exercisePanel.getAllExercises();
    for (Exercise ex : exercises) {
      exerciseDropdown.addItem(ex.toString());
    }
  }

  /**
   * Loads all existing workouts from WorkoutManager into the list.
   */
  private void loadWorkouts() {
    workoutListModel.clear();
    List<Workout> workouts = WorkoutManager.getInstance().getWorkouts();

    for (Workout workout : workouts) {
      workoutListModel.addElement(workout.getName() + " - " + workout.getDate());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonAddWorkout) {
      showAddWorkoutDialog();
    } else if (e.getSource() == buttonRemoveWorkout) {
      removeSelectedWorkout();
    } else if (e.getSource() == buttonAddExerciseToWorkout) {
      addExerciseToSelectedWorkout();
    }
  }

  /**
   * Displays a dialog for adding a new workout.
   */
  private void showAddWorkoutDialog() {
    JTextField nameField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField dateField = new JTextField("yyyy-MM-dd");

    JPanel panel = new JPanel(new GridLayout(3, 2));
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
   * Processes the input from the add workout dialog.
   *
   * @param nameField       The workout name field.
   * @param descriptionField The description field.
   * @param dateField        The date field.
   */
  private void processWorkoutInput(JTextField nameField, JTextField descriptionField, JTextField dateField) {
    try {
      String name = nameField.getText().trim();
      String description = descriptionField.getText().trim();
      Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());

      if (name.isEmpty() || description.isEmpty()) {
        throw new IllegalArgumentException("Workout name and description cannot be empty.");
      }

      Workout workout = new Workout(name, description, date);
      WorkoutManager.getInstance().addWorkout(workout);
      workoutListModel.addElement(workout.getName() + " - " + workout.getDate());

      outputHandler.print("Added Workout: " + workout.getName());

    } catch (ParseException e) {
      outputHandler.print("Invalid date format. Use yyyy-MM-dd.");
    } catch (IllegalArgumentException e) {
      outputHandler.print("Error: " + e.getMessage());
    }
  }

  /**
   * Removes the selected workout from the list.
   */
  private void removeSelectedWorkout() {
    int selectedIndex = workoutList.getSelectedIndex();
    if (selectedIndex != -1) {
      Workout workout = WorkoutManager.getInstance().getWorkouts().get(selectedIndex);
      WorkoutManager.getInstance().removeWorkout(workout);
      workoutListModel.remove(selectedIndex);

      outputHandler.print("Removed workout: " + workout.getName());
    } else {
      outputHandler.print("Please select a workout to remove.");
    }
  }

  /**
   * Adds a selected exercise to the currently selected workout.
   */
  private void addExerciseToSelectedWorkout() {
    int selectedIndex = workoutList.getSelectedIndex();
    if (selectedIndex == -1) {
      outputHandler.print("Select a workout first!");
      return;
    }

    Workout selectedWorkout = WorkoutManager.getInstance().getWorkouts().get(selectedIndex);
    String selectedExercise = (String) exerciseDropdown.getSelectedItem();

    if (selectedExercise == null) {
      outputHandler.print("No exercises available!");
      return;
    }

    for (Exercise ex : exercisePanel.getAllExercises()) {
      if (ex.toString().equals(selectedExercise)) {
        selectedWorkout.addExerciseToWorkout(ex);
        outputHandler.print("Added Exercise to Workout: " + ex);
        return;
      }
    }
  }
}
