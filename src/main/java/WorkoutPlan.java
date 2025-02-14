import java.time.LocalDate;
import java.util.*;

/**
 * Represents a workout plan with a calendar of workouts.
 * Each workout plan object has a name, description, and a calendar of workouts.
 * The calendar is a map where the key is a date and the value is a list of workouts.
 * Workouts are stored in a TreeMap so that the dates are sorted.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 *
 */

public class WorkoutPlan {

  private String name;
  private String description;
  private final TreeMap<LocalDate, List<Workout>> workoutCalendar;

  public WorkoutPlan(String name, String description) {
    setName(name);
    setDescription(description);
    this.workoutCalendar = new TreeMap<>();
  }

  /**
    * Adds a workout to the workout plan calendar.
    * If the date is not already in the calendar, a new list of workouts is created.
    * The workout is then added to the list of workouts for that date.
    * @param date the date of the workout
    * @param workout the workout to add
    * @throws IllegalArgumentException if date or workout is null
    * @throws IllegalArgumentException if the date is already in the calendar
   */
  public void addWorkout(LocalDate date, Workout workout){
    if (date == null || workout == null) {
      throw new IllegalArgumentException();
    }
    workoutCalendar.putIfAbsent(date, new ArrayList<>());
    workoutCalendar.get(date).add(workout);
  }

  /**
    * Removes a workout from the workout plan calendar.
    * If the date is not in the calendar, or the workout is not in the list of workouts for that date, an exception is thrown.
    * If the list of workouts for that date is empty after removing the workout, the date is removed from the calendar.
    * @param date the date of the workout
    * @param workout the workout to remove
    * @throws IllegalArgumentException if date or workout is null
    * @throws IllegalArgumentException if the date is not in the calendar
   */
  public void removeWorkout(LocalDate date, Workout workout) {
    if (date == null || workout == null || !workoutCalendar.containsKey(date)) {
      throw new IllegalArgumentException();
    }
    workoutCalendar.get(date).remove(workout);
    if (workoutCalendar.get(date).isEmpty()) {
      workoutCalendar.remove(date);
    }
  }

  /**
   * Returns a list of workouts for a given date.
   * If the date is not in the calendar, an empty list is returned.
   * @param date the date to get workouts for
   * @return a list of workouts for the given date
   */
  public List<Workout> getWorkoutsOnDate(LocalDate date) {
    return workoutCalendar.getOrDefault(date, new ArrayList<>());
  }

  /**
   * Returns a map of workouts between two dates.
   * The map includes workouts for the start date and end date.
   * If the start date is after the end date, an exception is thrown.
   * @param start the start date
   * @param end the end date
   * @return a map of workouts between the start and end date
   * @throws IllegalArgumentException if start date is after end date
   */
  public Map<LocalDate, List<Workout>> getWorkoutsBetween(LocalDate start, LocalDate end) {
    if (start == null || end == null || start.isAfter(end)) {
      throw new IllegalArgumentException();
    }
    return workoutCalendar.subMap(start, true, end, true);
  }

  /**
   * Displays the workout calendar.
   * The calendar is displayed in the format:
   * Date: [workout1, workout2, ...]
   */
  public void displayCalendar() {
    System.out.println("\nWorkout Calendar: " + name);
    for (Map.Entry<LocalDate, List<Workout>> entry : workoutCalendar.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  // Getters and Setters
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
