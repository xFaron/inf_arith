package arbritaryarithmetic;

public class AFloat extends ANumber {

	public AFloat() { 
    super(); 
  }

  public AFloat(String s) {
    for (char c : s.toCharArray()) {
      num_list.add(c - '0');
    }
  }

  public AFloat add(AFloat num1) {
    return new AFloat();
  }

  public AFloat sub(AFloat num1) {
    return new AFloat();
  }
  
  public AFloat mul(AFloat num1) {
    return new AFloat();
  }
  
  public AFloat div(AFloat num1) {
    return new AFloat();
  }

  @Override
  public String toString() {
    String converted = new String();

    for (int i : num_list) {
      converted += (i + '0');
    }

    return converted;
  }

}