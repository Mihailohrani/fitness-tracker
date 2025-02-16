import javax.swing.*;

/**
 * OutputHandler responsible for all output.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.16
 */
class OutputHandler {
  private final JTextArea outputArea;

  public OutputHandler(JTextArea outputArea) {
    this.outputArea = outputArea;
  }

  public void print(String message) {
    outputArea.append(message + "\n");
  }
}