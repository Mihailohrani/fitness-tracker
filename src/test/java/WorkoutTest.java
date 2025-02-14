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
    workout.addExercise(squat);
    assertEquals(1, workout.getExercises().size());
  }

  @Test
  void testRemoveExercise() {
    workout.addExercise(squat);
    workout.addExercise(benchPress);
    workout.removeExercise(squat);
    assertEquals(1, workout.getExercises().size());
    assertFalse(workout.getExercises().contains(squat));
  }

  @Test
  void testGetExercisesSorted() {
    workout.addExercise(squat);
    workout.addExercise(benchPress);

    List<Exercise> sortedExercises = workout.getExercises();
    assertEquals("Bench Press", sortedExercises.get(0).getName());
    assertEquals("Shoulder Press", sortedExercises.get(1).getName());
  }

  @Test
  void testAddNullExercise() {
    assertThrows(IllegalArgumentException.class, () -> workout.addExercise(null));
  }

  @Test
  void testRemoveNullExercise() {
    assertThrows(IllegalArgumentException.class, () -> workout.removeExercise(null));
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
