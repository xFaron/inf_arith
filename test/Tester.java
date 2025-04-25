import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Tester {
  private static final Logger LOGGER = Logger.getLogger(Tester.class.getName());
  
  static {
    ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new SimpleFormatter());
    LOGGER.addHandler(handler);
    LOGGER.setUseParentHandlers(false);
  }
  
  public static void main(String[] args) {
  		MyInfArith.testing = true;

      String inputFolderPath = "test/input";
      String outputFolderPath = "test/output";

      File inputFolder = new File(inputFolderPath);
      File outputFolder = new File(outputFolderPath);

      if (!inputFolder.exists() || !outputFolder.exists()) {
          LOGGER.warning(String.format("Some/All of these folders don't exist: %s %s", inputFolderPath, outputFolderPath));
          return;
      }

      String[] inputFileNames = inputFolder.list();
      if (inputFileNames == null || inputFileNames.length == 0) {
          LOGGER.warning("No input files found in " + inputFolderPath);
          return;
      }

      int totalTests = 0;
      int totalSuccessCount = 0;
      
      for (String inputFile : inputFileNames) {
          File input = new File(inputFolder, inputFile);
          File output = new File(outputFolder, inputFile); // Expecting same names

          if (!input.exists() || !output.exists()) {
              LOGGER.warning(String.format("Some/All of these files don't exist: %s/%s %s/%s", 
                  inputFolderPath, inputFile, outputFolderPath, inputFile));
              continue;
          }

          int[] results = processTestFile(input, output, inputFile);
          totalTests += results[0];
          totalSuccessCount += results[1];
      }
      
      LOGGER.info(String.format("SUMMARY: %d of %d tests passed (%.2f%%)", 
          totalSuccessCount, totalTests, totalTests > 0 ? (100.0 * totalSuccessCount / totalTests) : 0));
  }
  
  private static int[] processTestFile(File input, File output, String fileName) {
    int testCaseCount = 0;
    int successCount = 0;
    
    try (Scanner inputScanner = new Scanner(input);
       Scanner outputScanner = new Scanner(output)) {
      
      while(inputScanner.hasNextLine()) {
        testCaseCount += 1;
        String inputString = inputScanner.nextLine();
        
        if (outputScanner.hasNextLine()) {
          String outputString = outputScanner.nextLine();
          
          try {
            String resultString = MyInfArith.test(inputString.split(" "));
            
            if (resultString.equals(outputString)) {
              LOGGER.info(String.format("[%s:%d] SUCCESS : <%s> <%s> <%s>", fileName, testCaseCount, inputString, outputString, resultString));
              successCount += 1;
            } else {
              LOGGER.warning(String.format("[%s:%d] FAIL    : <%s> <%s> <%s>", fileName, testCaseCount, inputString, outputString, resultString));
            }
          } catch (Exception e) {
            LOGGER.severe(String.format("[%s:%d] ERROR   : Exception while processing <%s>: %s", fileName, testCaseCount, inputString, e.getMessage()));
          }
        } else {
          LOGGER.warning(String.format("[%s:%d] FAIL    : No Output string", fileName, testCaseCount));
        }
      }
        
      if (outputScanner.hasNextLine()) {
        LOGGER.warning(String.format("[%s] WARN    : Output file has more lines than input file", fileName));
      }
        
    } catch (FileNotFoundException e) {
      LOGGER.severe(String.format("Error opening test files: %s", e.getMessage()));
    }
    
    LOGGER.info(String.format("[%s] RESULT  : %d of %d tests passed", fileName, successCount, testCaseCount));
    return new int[]{testCaseCount, successCount};
  }
}