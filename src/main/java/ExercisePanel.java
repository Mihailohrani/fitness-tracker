import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ExercisePanel extends JPanel implements ActionListener {
  private final JButton buttonAddExercise;
  private final OutputHandler outputHandler;
  private final DefaultListModel<String> exerciseListModel;
  private final List<Exercise> exercises;

  public ExercisePanel() {
    setLayout(null);

    JLabel titleLabel = new JLabel("Exercise Management");
    titleLabel.setBounds(300, 10, 200, 30);
    add(titleLabel);

    buttonAddExercise = new JButton("Add Exercise");
    buttonAddExercise.setBounds(50, 50, 150, 30);
    buttonAddExercise.addActionListener(this);
    add(buttonAddExercise);

    exerciseListModel = new DefaultListModel<>();
    JList<String> exerciseList = new JList<>(exerciseListModel);
    JScrollPane listScrollPane = new JScrollPane(exerciseList);
    listScrollPane.setBounds(50, 100, 300, 200);
    add(listScrollPane);

    JTextArea outputArea = new JTextArea();
    outputArea.setEditable(false);
    JScrollPane outputScrollPane = new JScrollPane(outputArea);
    outputScrollPane.setBounds(50, 320, 500, 100);
    add(outputScrollPane);

    outputHandler = new OutputHandler(outputArea);
    exercises = new ArrayList<>();
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

    JPanel panel = createExerciseDialogPanel(nameField, repsField, setsField, weightField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add Exercise", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      processExerciseInput(nameField, repsField, setsField, weightField);
    }
  }

  /**
   * Creates a JPanel containing input fields for adding an exercise.
   */
  private JPanel createExerciseDialogPanel(JTextField nameField, JTextField repsField, JTextField setsField, JTextField weightField) {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setPreferredSize(new java.awt.Dimension(300, 150));

    JLabel nameLabel = new JLabel("Exercise Name:");
    nameLabel.setBounds(10, 10, 120, 25);
    nameField.setBounds(140, 10, 150, 25);

    JLabel repsLabel = new JLabel("Reps:");
    repsLabel.setBounds(10, 40, 120, 25);
    repsField.setBounds(140, 40, 150, 25);

    JLabel setsLabel = new JLabel("Sets:");
    setsLabel.setBounds(10, 70, 120, 25);
    setsField.setBounds(140, 70, 150, 25);

    JLabel weightLabel = new JLabel("Weight (kg):");
    weightLabel.setBounds(10, 100, 120, 25);
    weightField.setBounds(140, 100, 150, 25);

    panel.add(nameLabel);
    panel.add(nameField);
    panel.add(repsLabel);
    panel.add(repsField);
    panel.add(setsLabel);
    panel.add(setsField);
    panel.add(weightLabel);
    panel.add(weightField);

    return panel;
  }

  /**
   * Processes user input and adds a new exercise if valid.
   */
  private void processExerciseInput(JTextField nameField, JTextField repsField, JTextField setsField, JTextField weightField) {
    try {
      String name = nameField.getText().trim();
      int reps = Integer.parseInt(repsField.getText().trim());
      int sets = Integer.parseInt(setsField.getText().trim());
      int weight = Integer.parseInt(weightField.getText().trim());

      if (name.isEmpty() || reps < 0 || sets < 0 || weight < 0) {
        throw new IllegalArgumentException();
      }

      Exercise exercise = new Exercise(name, reps, sets, weight);
      exercises.add(exercise);
      exerciseListModel.addElement(exercise.toString());
      outputHandler.print("Added Exercise: " + exercise);

    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Please enter valid numbers for reps, sets, and weight.", "Input Error", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
