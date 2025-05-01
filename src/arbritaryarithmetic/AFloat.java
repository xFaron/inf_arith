package arbritaryarithmetic;

import java.lang.StringBuilder;
import java.util.ArrayList;

public class AFloat extends ANumber {
  public static int DECIMAL_LIMIT = 30;
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
      } else if (c == 'e') {
        try {
          power += Integer.parseInt(s.substring(i+1, s.length()));
        } catch (Exception e) {
          throw new IllegalArgumentException("Invalid power in input: " + c);
        }
      } else {
        throw new IllegalArgumentException("Invalid digit in input: " + c);
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

  public static AFloat parse(String s) {
    return new AFloat(s);
  }

  public AFloat add(AFloat num1) {
    AFloat result = new AFloat();
    int minPower = Math.min(this.power, num1.power);

    num1._change_power(minPower);
    this._change_power(minPower);

    int lenThis = this.num_list.size();
    int lenNum1 = num1.num_list.size();
    int lenMax = Math.max(lenThis, lenNum1);

    result.power = minPower;

    for (int i = 0; i < lenMax; i++) {
      int val_this = (i < lenThis) ? this.num_list.get(i) : 0;
      int val_num1 = (i < lenNum1) ? num1.num_list.get(i) : 0;
      int sum = val_this + val_num1;
      result.num_list.add(sum);
    }

    result._resolve();
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

    result.power = this.power + num1.power;
    result._resolve();
    return result;
  }
  
  public AFloat div(AFloat num) {
    AFloat num2 = new AFloat(this);
    AFloat num1 = new AFloat(num);
    
    int originalPowerNum1 = num1.power;
    int orginalPowerNum2 = num2.power;

    num2._shift_left(DECIMAL_LIMIT);

    AInteger num2Int = new AInteger();
    num2Int.num_list = new ArrayList(num2.num_list);
    AInteger num1Int = new AInteger();
    num1Int.num_list = new ArrayList(num1.num_list);

    AInteger result = num2Int.div(num1Int);
    AFloat resultFloat = new AFloat();
    resultFloat.num_list = new ArrayList(result.num_list);

    resultFloat.power = orginalPowerNum2 - originalPowerNum1 - DECIMAL_LIMIT;
    System.out.println(resultFloat.power + " = " + orginalPowerNum2 + " - " + originalPowerNum1 + " - " + DECIMAL_LIMIT);

    if (resultFloat.power < -DECIMAL_LIMIT) {
      resultFloat._change_power(-DECIMAL_LIMIT);
    }

    resultFloat._resolve();
    return resultFloat;
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

      for (int i = num_list.size() - 1; i > 0; i--) {
        if (sign * num_list.get(i-1) < 0 || (num_list.get(i-1) == 0 && i-2 >= 0 && sign * num_list.get(i-2) < 0)) {
          _resolve_aux(i, sign);
        }
      }
      
    }

    int extraZeroes = 0;
    while (extraZeroes < this.power * -1 && this.num_list.get(extraZeroes) == 0) {
      extraZeroes += 1;
    }

    this._change_power(this.power + extraZeroes);
    return new AFloat(this);
  }

  private void _resolve_aux(int i, int sign) {
    if (num_list.get(i) == 0) {
      this._resolve_aux(i + 1, sign);
    }
    num_list.set(i, num_list.get(i) - sign);
    num_list.set(i-1, num_list.get(i-1) + 10 * sign);
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
    
    return result.toString();
  }
}