import arbritaryarithmetic.*;

public class MyInfArith {
  public static boolean testing = false;

  public static void main(String[] args) {
    run_command(args);
  }

  public static String run_command(String args[]) {
    if (args.length != 4) {
      logInfo("USAGE : java MyInfArith (int|float) (add|sub|mul|div) num1 num2");
      return new String("USAGE : java MyInfArith (int|float) (add|sub|mul|div) num1 num2");
    }

    String numType = args[0];
    String operation = args[1];
    String num1Str = args[2];
    String num2Str = args[3];

    try {

      if ("int".equals(numType)) {
        AInteger num1 = new AInteger(num1Str);
        AInteger num2 = new AInteger(num2Str);

        AInteger result;

        switch (operation) {
        case "add":
          result = num1.add(num2);
          break;
        case "sub":
          result = num1.sub(num2);
          break;
        case "mul":
          result = num1.mul(num2);
          break;
        case "div":
          result = num1.div(num2);
          break;
        default:
          throw new IllegalArgumentException(
            String.format("ARG ERROR: '%s' is not a supported operation. Use add, sub, mul, or div", operation)
          );
        }

        logInfo(String.format("RESULT : %s", result.toString()));
        return result.toString();
      } else if ("float".equals(numType)) {
        AFloat num1 = new AFloat(num1Str);
        AFloat num2 = new AFloat(num2Str);

        AFloat result;

        switch (operation) {
        case "add":
          result = num1.add(num2);
          break;
        case "sub":
          result = num1.sub(num2);
          break;
        case "mul":
          result = num1.mul(num2);
          break;
        case "div":
          result = num1.div(num2);
          break;
        default:
          throw new IllegalArgumentException(
            String.format("ARG ERROR: '%s' is not a supported operation. Use add, sub, mul, or div", operation)
          );
        }

        logInfo(String.format("RESULT : %s", result.toString()));
        return result.toString();
      } else {
        throw new IllegalArgumentException(
          String.format("ARG ERROR: '%s' is not a valid type. Use 'int' or 'float'", numType)
        );
      }

      
    } catch (Exception e) {
      logError(e.getMessage());
      return e.getMessage();
    }
  }

  private static void __logger(String message, boolean isError) {
    if (!testing) {System.out.println(String.format("%s - %s", isError ? "ERROR" : "INFO", message)); }
  }

  private static void logError(String message) {
    __logger(message, true);
  }

  private static void logInfo(String message) {
    __logger(message, false);
  }
}
