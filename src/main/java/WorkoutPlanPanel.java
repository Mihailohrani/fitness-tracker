import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * The {@code WorkoutPlanPanel} class represents the main panel for managing a weekly workout plan.
 * It consists of a vertical list of days on the left and a workout details section on the right.
 * Users can click on a day to view and manage workouts for that specific day.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.21
 */
public class WorkoutPlanPanel extends JPanel {
  private final WorkoutPlan workoutPlan;
  private final JList<String> dayList;
  private final WorkoutDetailsPanel workoutDetailsPanel;

  /**
   * Constructs a new {@code WorkoutPlanPanel} with a weekly view.
   * It initializes the day list (Monday-Sunday) and sets up the workout details panel.
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
    dayList.setFont(new Font("Arial", Font.BOLD, 16));
    dayList.setFixedCellHeight(50);
    dayList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
    dayScrollPane.setPreferredSize(new Dimension(180, 300));

    workoutDetailsPanel = new WorkoutDetailsPanel(workoutPlan);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dayScrollPane, workoutDetailsPanel);
    splitPane.setDividerLocation(180);
    splitPane.setResizeWeight(0.2);
    splitPane.setDividerSize(2);

    add(splitPane, BorderLayout.CENTER);
  }
}
