package arbritaryarithmetic;

import java.util.ArrayList;

abstract class ANumber {
  private ArrayList<Integer> num_list;

  public ANumber() {
    num_list = new ArrayList<>();
    num_list.add(0);
  }

  // TODO //
  // Can define abstract methods to imply that add, sub, mul and div are req funcs //
}

public class AInteger extends ANumber {

  public AInteger() { 
    super(); 
  }

  public AInteger(String s) {
    for (char c : s.toCharArray()) {
      
    }
  }

  public AInteger add(AInteger num1, AInteger num2) {
    return new AInteger();
  }

  // public AInteger sub(AInteger num1, AInteger num2) {
  //   return new AInteger();
  // }

  // public AInteger mul(AInteger num1, AInteger num2) {
  //   return new AInteger();
  // }
  
  // public AInteger div(AInteger num1, AInteger num2) {
  //   return new AInteger();
  // }
}