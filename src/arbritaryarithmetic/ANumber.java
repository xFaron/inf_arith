package arbritaryarithmetic;

import java.util.ArrayList;

public abstract class ANumber {
  protected static String ZERO_DIVISION_ERROR = "ZeroDivisionError";
  protected ArrayList<Integer> num_list;

  abstract public String toString();
}