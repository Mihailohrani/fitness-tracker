import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The WorkoutPanel class allows users to manage workouts.
 * Users can add, remove workouts and assign exercises to workouts.
 * It also provides a dropdown list of all available exercises.
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

    outputHandler = new OutputHandler(new JTextArea());

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
   * Loads all existing workouts from WorkoutManager into the workout list.
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
      outputHandler.print("Added Workout");
    } else if (e.getSource() == buttonRemoveWorkout) {
      outputHandler.print("Removed Workout");
    } else if (e.getSource() == buttonAddExerciseToWorkout) {
      addExerciseToSelectedWorkout();
    }
  }

  /**
   * Adds a selected exercise to the currently selected workout.
   */
  private void addExerciseToSelectedWorkout() {
    int selectedIndex = workoutList.getSelectedIndex();
    if (selectedIndex == -1) {
      JOptionPane.showMessageDialog(this, "Select a workout first!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Workout selectedWorkout = WorkoutManager.getInstance().getWorkouts().get(selectedIndex);
    String selectedExercise = (String) exerciseDropdown.getSelectedItem();

    if (selectedExercise == null) {
      JOptionPane.showMessageDialog(this, "No exercises available!", "Error", JOptionPane.ERROR_MESSAGE);
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
