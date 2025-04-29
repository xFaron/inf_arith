package arbritaryarithmetic;

import java.lang.StringBuilder;
import java.util.ArrayList;

public class AFloat extends ANumber {
  private int power;

  public AFloat() { 
    super();
    power = 0;
  }

  public AFloat(String s) {
    if (s == null || s.isEmpty()) {
      return;
    }
    
    boolean isNegative = false;
    boolean decimalPointNotDetected = true;
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
        if (decimalPointNotDetected) {
          power += -1;
        }
      } else if (c == '.') {
        decimalPointNotDetected = false;
      }
    }

    if (decimalPointNotDetected) {
      power = 0;
    }
  }

  public AFloat(AFloat num) {
    for (int digit : num.num_list) {
      this.num_list.add(digit);
    }
    this.power = num.power;
  }

  public AFloat add(AFloat num1) {
    System.out.println("=== Started Addition ===");

    // Print current object state
    System.out.println("this.num_list: " + this.num_list);
    System.out.println("this.power: " + this.power);

    // Print incoming object state
    System.out.println("num1.num_list: " + num1.num_list);
    System.out.println("num1.power: " + num1.power);

    AFloat result = new AFloat();
    int minPower = Math.min(this.power, num1.power);

    System.out.println("Changing powers to minimum of both: " + minPower);
    num1._change_power(minPower);
    this._change_power(minPower);

    int lenThis = this.num_list.size();
    int lenNum1 = num1.num_list.size();
    int lenMax = Math.max(lenThis, lenNum1);

    System.out.println("After power change:");
    System.out.println("this.num_list: " + this.num_list);
    System.out.println("this.power: " + this.power);
    System.out.println("num1.num_list: " + num1.num_list);
    System.out.println("num1.power: " + num1.power);

    result.power = minPower;

    for (int i = 0; i < lenMax; i++) {
      int val_this = (i < lenThis) ? this.num_list.get(i) : 0;
      int val_num1 = (i < lenNum1) ? num1.num_list.get(i) : 0;
      int sum = val_this + val_num1;
      result.num_list.add(sum);

      System.out.printf("Index %d: this = %d, num1 = %d, sum = %d%n", i, val_this, val_num1, sum);
      System.out.println("Current status:");
      System.out.println("this.num_list: " + this.num_list);
      System.out.println("num1.num_list: " + num1.num_list);

    }

    System.out.println("Before resolving result: " + result.num_list + " | power: " + result.power);
    result._resolve();
    System.out.println("After resolving result: " + result.num_list + " | power: " + result.power);

    System.out.println("=== Finished Addition ===");
    return result;
  }


  public AFloat sub(AFloat num1) {
    AFloat temp = new AFloat(num1);
    return this.add(temp._negate());
  }
  
  public AFloat mul(AFloat num1) {
    int shift_down = 0;
    AFloat result = new AFloat();
    AFloat temp = new AFloat(num1);

    while (temp.num_list.size() > 0) {
      int digit = temp.num_list.get(0);

      int len = this.num_list.size();
      for (int i = 0; i < len; i++) {
        try {
          result.num_list.set(i + shift_down, result.num_list.get(i + shift_down) + this.num_list.get(i) * digit);
        } catch (Exception e) {
          result.num_list.add(this.num_list.get(i) * digit);
        }
      }

      temp._shift_right(1);
      shift_down += 1;
    }

    result._resolve();
    return result;
  }
  
  public AFloat div(AFloat num1) {
    // Division By Zero check
    if (num1.equals(new AFloat("0")) || num1.equals(new AFloat())) {
      throw new IllegalArgumentException(ZERO_DIVISION_ERROR);
    }

    // Initialising variables //
    AFloat quotient = new AFloat();
    AFloat reminder = new AFloat(this);
    reminder._absolute();

    AFloat divisor = new AFloat(num1);
    divisor._absolute();

    boolean isNegative = this._is_negative() ^ num1._is_negative();
    num1._absolute();
    // //

    for (int i = 0; i < (this.num_list.size() - num1.num_list.size()); i++) {
      divisor._shift_left(1);
    }

    AFloat result = (_div_aux(quotient, reminder, divisor, num1)).mul(new AFloat(isNegative ? "-1" : "1"));
    return result;
  }

  private AFloat _div_aux(AFloat quotient, AFloat reminder, AFloat curr_divisor, AFloat divisor) {
    while (!(reminder.sub(curr_divisor))._is_negative()) {
      reminder = reminder.sub(curr_divisor);
      quotient = quotient.add(new AFloat("1"));
    }

    if (curr_divisor.equals(divisor)) {
      if (quotient.num_list.isEmpty()) {
        quotient = new AFloat("0");
      }

      return quotient;
    }

    quotient._shift_left(1);
    curr_divisor._shift_right(1);

    AFloat result = _div_aux(quotient, reminder, curr_divisor, divisor);
    return result;
  }

  // No bugs
  private AFloat _shift_left(int n) {
    for (int i = 0; i < n; i++) {
      this.num_list.add(0, 0);
    }

    AFloat shifted = new AFloat(this);
    return shifted;
  }

  // No bugs
  private AFloat _shift_right(int n) {

    for (int i = 0; i < n; i++) {
      if (!this.num_list.isEmpty()) {
        this.num_list.remove(0);
      } 
    }

    AFloat shifted = new AFloat(this);
    return shifted;
  }

  // No bugs
  private AFloat _change_power(int n) {
    int delta_power = n - this.power;
    System.out.println("The numbers > delta_power:" + delta_power + "|n: " + n + "|power: " + this.power);
    if (delta_power > 0) {
      this._shift_right(delta_power);
    } else if (delta_power < 0) {
      this._shift_left(-delta_power);
    } 

    this.power += delta_power;

    AFloat changed = new AFloat(this);
    return changed;
  }


  private AFloat _resolve() {
    if (this.num_list.isEmpty()) {
      this.num_list.add(0);
    } else {

      int carry = 0;
      int temp_buffer = 0;
      int sign = 1;
      
      for (int i = 0; i < num_list.size(); i++) {
        temp_buffer = num_list.get(i) + carry;
        num_list.set(i, temp_buffer % 10);
        carry = temp_buffer / 10;
        if (sign * (temp_buffer % 10) < 0) {
          sign *= -1;
        }
      }

      while (carry != 0) {
        num_list.add(carry % 10);
        carry /= 10;
      }

      boolean changes = false;
      do {
        for (int i = num_list.size() - 1; i > 0; i--) {
          changes = false;
          if (sign * num_list.get(i-1) < 0 || (num_list.get(i-1) == 0 && i-2 >= 0 && sign * num_list.get(i-2) < 0)) {
            num_list.set(i, num_list.get(i) - sign);
            num_list.set(i-1, num_list.get(i-1) + 10 * sign);
            changes = true;
          }
        }
      } while (changes);
      
    }

    int extraZeroes = 0;
    while (extraZeroes < this.power * -1 && this.num_list.get(extraZeroes) == 0) {
      extraZeroes += 1;
    }

    this._change_power(this.power + extraZeroes);
    return new AFloat(this);
  }

  private AFloat _negate() {
    for (int i = 0; i < this.num_list.size(); i++) {
      this.num_list.set(i, this.num_list.get(i) * -1);
    }

    return new AFloat(this);
  }

  private AFloat _absolute() {
    if (this._is_negative()) {
      this._negate();
    }

    return new AFloat(this);
  }

  private boolean _is_negative() {
    this._resolve();
    for (int i = 0; i < this.num_list.size(); i++) {
      if (this.num_list.get(i) < 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof AFloat && ((AFloat) o).num_list.equals(this.num_list) && ((AFloat) o).power == this.power) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    System.out.println("Started printing");
    System.out.println("Trying to print: " + this.num_list + " | power: " + this.power);
    if (this.num_list.isEmpty()) {
      return "0.0";
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
    // int endPower = Math.min(power * -1 + this.num_list.size() + 1, -1);
    // int startPower = Math.max(0, this.power + this.num_list.size() - 1);

    System.out.println("Number List: " + this.num_list);
    System.out.println("Power: " + this.power);

    for (int i = Math.max(num_list.size() + this.power, 0); i >= Math.min(-1, this.power); i--) {
      int digit;
      try {
        digit = num_list.get(i - this.power);
      } catch (Exception e) {
        digit = 0;
      }

      int absDigit = Math.abs(digit);

      if (leadingZeros && absDigit == 0 && i > 0) {
        continue;
      }

      leadingZeros = false;
      result.append((char)(absDigit + '0'));

      if (i == 0) {
        result.append('.');
      }
    }
    
    if (leadingZeros) {
      return "0.0";
    }
    
    System.out.println("Finished printing");
    return result.toString();
  }
}