import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * A simple workout planner with a vertical list of days on the left and
 * a workout panel on the right.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.21
 */
public class WorkoutPlanPanel extends JPanel {
  private final WorkoutPlan workoutPlan;
  private final JList<String> dayList;
  private final WorkoutDetailsPanel workoutDetailsPanel;

  /**
   * Creates a new WorkoutPlanPanel.
   * Initializes the workout plan and the workout details panel.
   */
  public WorkoutPlanPanel() {
    setLayout(new BorderLayout());

    workoutPlan = new WorkoutPlan("My Weekly Plan", "A 7-day workout schedule");

    DefaultListModel<String> dayListModel = new DefaultListModel<>();
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    for (String day : days) {
      dayListModel.addElement(day);
    }

    dayList = new JList<>(dayListModel);
    dayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    dayList.setFixedCellHeight(50);
    dayList.setFont(new Font("Arial", Font.BOLD, 16));

    dayList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int index = dayList.getSelectedIndex();
        if (index != -1) {
          LocalDate selectedDate = LocalDate.now().with(DayOfWeek.MONDAY).plusDays(index);
          workoutDetailsPanel.updateWorkoutDetails(selectedDate);
        }
      }
    });

    JScrollPane dayScrollPane = new JScrollPane(dayList);
    dayScrollPane.setPreferredSize(new Dimension(150, 300));
    add(dayScrollPane, BorderLayout.WEST);

    workoutDetailsPanel = new WorkoutDetailsPanel(workoutPlan);
    add(workoutDetailsPanel, BorderLayout.CENTER);
  }
}
