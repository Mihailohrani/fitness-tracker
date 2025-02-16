import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {
  private Workout workout;
  private Exercise squat;
  private Exercise benchPress;

  @BeforeEach
  void setUp() {
    workout = new Workout("Strength Training", "Push day focusing on shoulders", new Date());
    squat = new Exercise("Shoulder Press", 10, 3, 100);
    benchPress = new Exercise("Bench Press", 8, 3, 90);
  }

  @Test
  void testAddExercise() {
    workout.addExerciseToWorkout(squat);
    assertEquals(1, workout.getExercisesInWorkout().size());
  }

  @Test
  void testRemoveExercise() {
    workout.addExerciseToWorkout(squat);
    workout.addExerciseToWorkout(benchPress);
    workout.removeExerciseFromWorkout(squat);
    assertEquals(1, workout.getExercisesInWorkout().size());
    assertFalse(workout.getExercisesInWorkout().contains(squat));
  }

  @Test
  void testGetExercisesSorted() {
    workout.addExerciseToWorkout(squat);
    workout.addExerciseToWorkout(benchPress);

    List<Exercise> sortedExercises = workout.getExercisesInWorkout();
    assertEquals("Bench Press", sortedExercises.get(0).getName());
    assertEquals("Shoulder Press", sortedExercises.get(1).getName());
  }

  @Test
  void testAddNullExercise() {
    assertThrows(IllegalArgumentException.class, () -> workout.addExerciseToWorkout(null));
  }

  @Test
  void testRemoveNullExercise() {
    assertThrows(IllegalArgumentException.class, () -> workout.removeExerciseFromWorkout(null));
  }

  @Test
  void testSetInvalidName() {
    assertThrows(IllegalArgumentException.class, () -> workout.setName(""));
    assertThrows(IllegalArgumentException.class, () -> workout.setName(null));
  }

  @Test
  void testSetInvalidDescription() {
    assertThrows(IllegalArgumentException.class, () -> workout.setDescription(""));
    assertThrows(IllegalArgumentException.class, () -> workout.setDescription(null));
  }

  @Test
  void testSetInvalidDate() {
    assertThrows(IllegalArgumentException.class, () -> workout.setDate(null));
  }
}
