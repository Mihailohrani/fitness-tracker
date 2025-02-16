import javax.swing.*;

/**
 * The main class for the Fitness Tracker application.
 * This class is responsible for launching the GUI.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */
public class Launcher {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      FitnessTrackerGUI gui = new FitnessTrackerGUI();
      gui.setVisible(true);
    });
  }
}
