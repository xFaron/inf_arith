package arbritaryarithmetic;

import java.lang.StringBuilder;

public class AInteger extends ANumber {

  public AInteger() { 
    super(); 
  }

  public AInteger(String s) {
    if (s == null || s.isEmpty()) {
      return;
    }
    
    boolean isNegative = false;
    int startIndex = 0;
    
    if (s.charAt(0) == '-') {
      isNegative = true;
      startIndex = 1;
    }
    
    for (int i = s.length() - 1; i >= startIndex; i--) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int digit = c - '0';
        if (isNegative) {
          digit = -digit;
        }
        num_list.add(digit);
      } else {
        throw new IllegalArgumentException("Invalid digit in input: " + c);
      }
    }
  }

  public AInteger(AInteger num) {
    for (int digit : num.num_list) {
      this.num_list.add(digit);
    }
  }

  public AInteger add(AInteger num1) {
    AInteger result = new AInteger();

    int lenThis = this.num_list.size();
    int lenNum1 = num1.num_list.size();
    int lenMax = Math.max(lenThis, lenNum1);

    for (int i = 0; i < lenMax; i++) {

      int val_this = (i < lenThis) ? this.num_list.get(i) : 0;
      int val_num1 = (i < lenNum1) ? num1.num_list.get(i) : 0;
      result.num_list.add(val_num1 + val_this);
    }

    result._resolve();
    return result;
  }

  public AInteger sub(AInteger num1) {
    return add(num1._negate());
  }
  
  public AInteger mul(AInteger num1) {
    return new AInteger();
  }
  
  public AInteger div(AInteger num1) {
    return new AInteger();
  }

  private AInteger _resolve() {
    AInteger result = new AInteger();

    int carry = 0;
    int temp_buffer = 0;
    
    for (int i = 0; i < num_list.size(); i++) {
      temp_buffer = num_list.get(i) + carry;
      num_list.set(i, temp_buffer % 10);
      carry = temp_buffer / 10;
    }

    if (carry != 0) {
      num_list.add(carry);
    }

    int sign = num_list.get(num_list.size() - 1) >= 0 ? 1 : -1;
    
    for (int i = num_list.size() - 1; i > 0; i--) {

      if (sign * num_list.get(i-1) < 0 || (num_list.get(i-1) == 0 && i-2 >= 0 && sign * num_list.get(i-2) < 0)) {
        num_list.set(i, num_list.get(i) - sign);
        num_list.set(i-1, num_list.get(i-1) + 10 * sign);
      }
    }

    return new AInteger(this);
  }

  private AInteger _negate() {
    AInteger result = new AInteger(this);

    for (int i = 0; i < result.num_list.size(); i++) {
      result.num_list.set(i, result.num_list.get(i) * -1);
    }

    return result;
  }

  @Override
  public String toString() {
    if (this.num_list.isEmpty()) {
      return "0";
    }
    
    boolean isNegative = false;
    for (int i = num_list.size() - 1; i >= 0; i--) {
      if (num_list.get(i) < 0) {
        isNegative = true;
        break;
      }
    }
    
    StringBuilder result = new StringBuilder();
    
    if (isNegative) {
      result.append('-');
    }
    
    boolean leadingZeros = true;
    for (int i = num_list.size() - 1; i >= 0; i--) {
      int digit = num_list.get(i);
      int absDigit = Math.abs(digit);

      if (leadingZeros && absDigit == 0 && i > 0) {
        continue;
      }

      leadingZeros = false;
      result.append((char)(absDigit + '0'));
    }
    
    if (leadingZeros) {
      return "0";
    }
    
    return result.toString();
  }

}