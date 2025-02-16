import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a workout with a list of exercises in it.
 * Each workout object has a name, description, list of exercises, and a date.
 *
 * @author Mihailo Hranisavljevic
 * @version 14.02.2025
 */
public class Workout {

  private String name;
  private String description;
  private final List<Exercise> exercises;
  private Date date;


  public Workout(String name, String description, Date date) {
    setName(name);
    setDescription(description);
    this.exercises = new ArrayList<>();
    setDate(date);
  }

  /**
   * Adds an exercise to the workout.
   *
   * @param exercise the exercise to add
   * @throws IllegalArgumentException if exercise is null
   */
  public void addExerciseToWorkout(Exercise exercise) {
    if (exercise == null) {
      throw new IllegalArgumentException();
    }
    exercises.add(exercise);
  }

  /**
   * Removes an exercise from the workout.
   *
   * @param exercise the exercise to remove
   * @throws IllegalArgumentException if exercise is null
   */
  public void removeExerciseFromWorkout(Exercise exercise) {
    if(exercise == null) {
      throw new IllegalArgumentException();
    }
    exercises.remove(exercise);
  }

  /**
   * Returns a list of exercises sorted alphabetically.
   *
   * @return a list of exercises sorted alphabetically
   */
  public List<Exercise> getExercisesInWorkout() {
    List<Exercise> sortedExercises = new ArrayList<>(exercises);
    sortedExercises.sort(Comparator.comparing(Exercise::getName));
    return sortedExercises;
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

  public void setDate(Date date) {
    if (date == null) {
      throw new IllegalArgumentException();
    }
    this.date = date;
  }

  public Date getDate() {
    return date;
  }






}
