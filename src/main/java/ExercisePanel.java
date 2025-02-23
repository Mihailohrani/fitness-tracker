import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The ExercisePanel class allows users to manage exercises.
 * Users can create new exercises, which are then stored in the global list.
 * This panel is linked with WorkoutPanel to provide available exercises dynamically.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.23
 */
public class ExercisePanel extends JPanel implements ActionListener {
  private final DefaultListModel<String> exerciseListModel;
  private final JList<String> exerciseList;
  private final OutputHandler outputHandler;
  private final List<Exercise> allExercises;
  private final WorkoutPanel workoutPanel; // Reference to update dropdown

  /**
   * Constructs an ExercisePanel that allows users to manage exercises.
   *
   * @param workoutPanel The associated WorkoutPanel to update exercises dynamically.
   */
  public ExercisePanel(WorkoutPanel workoutPanel) {
    this.workoutPanel = workoutPanel;
    setLayout(new BorderLayout());

    JButton buttonAddExercise = new JButton("Add Exercise");
    buttonAddExercise.addActionListener(this);
    add(buttonAddExercise, BorderLayout.NORTH);

    exerciseListModel = new DefaultListModel<>();
    exerciseList = new JList<>(exerciseListModel);
    add(new JScrollPane(exerciseList), BorderLayout.CENTER);

    outputHandler = new OutputHandler(new JTextArea());
    allExercises = new ArrayList<>();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    showAddExerciseDialog();
  }

  /**
   * Displays a dialog for adding a new exercise.
   */
  private void showAddExerciseDialog() {
    JTextField nameField = new JTextField();
    JTextField repsField = new JTextField();
    JTextField setsField = new JTextField();
    JTextField weightField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(4, 2));
    panel.add(new JLabel("Exercise Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Reps:"));
    panel.add(repsField);
    panel.add(new JLabel("Sets:"));
    panel.add(setsField);
    panel.add(new JLabel("Weight (kg):"));
    panel.add(weightField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add Exercise", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      processExerciseInput(nameField, repsField, setsField, weightField);
    }
  }

  /**
   * Processes the input from the add exercise dialog and adds the exercise to the global list.
   */
  private void processExerciseInput(JTextField nameField, JTextField repsField, JTextField setsField, JTextField weightField) {
    try {
      String name = nameField.getText().trim();
      int reps = Integer.parseInt(repsField.getText().trim());
      int sets = Integer.parseInt(setsField.getText().trim());
      int weight = Integer.parseInt(weightField.getText().trim());

      if (name.isEmpty() || reps < 1 || sets < 1 || weight < 0) {
        throw new IllegalArgumentException("Values must be valid numbers!");
      }

      Exercise exercise = new Exercise(name, reps, sets, weight);
      allExercises.add(exercise);

      exerciseListModel.addElement(exercise.toString());
      outputHandler.print("Added Exercise: " + exercise);

      workoutPanel.updateExerciseDropdown();

    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Retrieves the list of all exercises.
   *
   * @return A list of exercises.
   */
  public List<Exercise> getAllExercises() {
    return allExercises;
  }
}
