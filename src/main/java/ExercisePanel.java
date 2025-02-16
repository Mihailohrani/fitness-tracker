import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for the exercise tab.
 * Used for management of exercises.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */

public class ExercisePanel extends JPanel implements ActionListener {
  private final JButton buttonAddExercise;
  private final OutputHandler outputHandler;

  public ExercisePanel() {
    setLayout(new BorderLayout());
    add(new JLabel("Exercise Management", SwingConstants.CENTER), BorderLayout.PAGE_START);

    buttonAddExercise = new JButton("Add Exercise");
    buttonAddExercise.addActionListener(this);
    add(buttonAddExercise, BorderLayout.CENTER);

    JTextArea outputArea = new JTextArea(10, 30);
    outputArea.setEditable(false);
    add(new JScrollPane(outputArea), BorderLayout.SOUTH);

    outputHandler = new OutputHandler(outputArea);
  }

  /**
   * Method to handle button clicks.
   *
   * @param e ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonAddExercise) {
      showAddExerciseDialog();
    }
  }


  /**
   * Method to show dialog for adding an exercise.
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
    panel.add(new JLabel("Weight (lbs):"));
    panel.add(weightField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add Exercise", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      String name = nameField.getText();
      int reps = Integer.parseInt(repsField.getText());
      int sets = Integer.parseInt(setsField.getText());
      int weight = Integer.parseInt(weightField.getText());

      outputHandler.print("Added Exercise: " + name + " " + reps + "x" + sets + " " + weight + "lbs");
    }
  }
}
