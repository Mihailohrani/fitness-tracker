/**
 * This class represents an exercise.
 * An exercise has a name, number of reps, number of sets, and weight.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */

public class Exercise {
  private String name;
  private int reps;
  private int sets;
  private int weight;

  public Exercise(String name, int reps, int sets, int weight) {
    setName(name);
    setReps(reps);
    setSets(sets);
    setWeight(weight);
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
  public void setReps(int reps) {
    if (reps<0) {
      throw new IllegalArgumentException();
    }
    this.reps = reps;
  }
  public int getReps() {
    return reps;
  }
  public void setSets(int sets) {
    if (sets<0) {
      throw new IllegalArgumentException();
    }
    this.sets = sets;
  }
  public int getSets() {
    return sets;
  }
  public void setWeight(int weight) {
    if (weight<0) {
      throw new IllegalArgumentException();
    }
    this.weight = weight;
  }
  public int getWeight() {
    return weight;
  }
  // Returns a string representation of the exercise
  @Override
  public String toString() {
    return name + " " + reps + "x" + sets + " " + weight + "lbs";
  }
}

