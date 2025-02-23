import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Panel for the exercise tab.
 * Used for management of exercises.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.23
 */
  public class ExercisePanel extends JPanel implements ActionListener {
    private final JButton buttonAddExercise;
    private final DefaultListModel<String> exerciseListModel;
    private final OutputHandler outputHandler;

    /**
     * Constructs an ExercisePanel object.
     */
    public ExercisePanel() {
      setLayout(new BorderLayout());

      buttonAddExercise = new JButton("Add Exercise");
      buttonAddExercise.addActionListener(this);
      add(buttonAddExercise, BorderLayout.NORTH);

      exerciseListModel = new DefaultListModel<>();
      JList<String> exerciseList = new JList<>(exerciseListModel);
      add(new JScrollPane(exerciseList), BorderLayout.CENTER);

      outputHandler = new OutputHandler(new JTextArea());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonAddExercise) {
        showAddExerciseDialog();
      }
    }

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
     * Processes the input from the user and adds a new exercise to the list.
     *
     * @param nameField   The text field for the exercise name.
     * @param repsField   The text field for the number of repetitions.
     * @param setsField   The text field for the number of sets.
     * @param weightField The text field for the weight.
     */
    private void processExerciseInput(JTextField nameField, JTextField repsField, JTextField setsField, JTextField weightField) {
      try {
        String name = nameField.getText().trim();
        int reps = Integer.parseInt(repsField.getText().trim());
        int sets = Integer.parseInt(setsField.getText().trim());
        int weight = Integer.parseInt(weightField.getText().trim());

        if (name.isEmpty() || reps < 0 || sets < 0 || weight < 0) {
          throw new IllegalArgumentException("Values must be positive!");
        }

        Exercise exercise = new Exercise(name, reps, sets, weight);
        WorkoutManager.getInstance().getWorkouts().getFirst().addExerciseToWorkout(exercise);

        exerciseListModel.addElement(exercise.toString());
        outputHandler.print("Added Exercise: " + exercise);

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }