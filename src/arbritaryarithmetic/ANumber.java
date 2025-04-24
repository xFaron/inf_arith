package arbritaryarithmetic;

import java.util.ArrayList;

public abstract class ANumber {
  protected ArrayList<Integer> num_list;

  public ANumber() {
    num_list = new ArrayList<>();
  }

  abstract public String toString();
}