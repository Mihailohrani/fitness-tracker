import java.util.*;

/**
 * The {@code WorkoutManager} class represents a manager for workouts.
 * It stores a list of workouts and provides methods for adding and removing workouts.
 * This class is a singleton, meaning that only one instance can exist at a time.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.23
 */

public class WorkoutManager {
  private static WorkoutManager instance;
  private final List<Workout> workouts;

  /**
   * Constructs a new WorkoutManager object.
   */
  private WorkoutManager() {
    this.workouts = new ArrayList<>();
  }

  /**
   * Returns the instance of the WorkoutManager.
   * If the instance does not exist, a new instance is created.
   * @return the instance of the WorkoutManager
   */
  public static WorkoutManager getInstance() {
    if (instance == null) {
      instance = new WorkoutManager();
    }
    return instance;
  }

  /**
   * Adds a workout to the list of workouts.
   * @param workout the workout to add
   * @throws IllegalArgumentException if workout is null
   */
  public void addWorkout(Workout workout) {
    if (workout == null) throw new IllegalArgumentException("Workout cannot be null");
    workouts.add(workout);
  }

  /**
   * Returns an unmodifiable list of workouts.
   * @return an unmodifiable list of workouts
   */
  public List<Workout> getWorkouts() {
    return Collections.unmodifiableList(workouts);
  }

  /**
   * Removes a workout from the list of workouts.
   * @param workout the workout to remove
   */
  public void removeWorkout(Workout workout) {
    workouts.remove(workout);
  }

  private void loadDefaultWorkouts() {
    Workout defaultWorkout = new Workout("Full Body", "A balanced full-body workout", new Date());
    defaultWorkout.addExerciseToWorkout(new Exercise("Bench Press", 8, 4, 80));
    defaultWorkout.addExerciseToWorkout(new Exercise("Squat", 10, 4, 100));
    defaultWorkout.addExerciseToWorkout(new Exercise("Deadlift", 5, 3, 120));
    workouts.add(defaultWorkout);
  }
}
