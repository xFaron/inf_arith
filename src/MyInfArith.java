public class MyInfArith {
	public static void main(String[] args) {
		if (args.length != 4) {
			logInfo("USAGE : java MyInfArith");
		}

		String numType = args[0];
		String operation = args[1];
		String num1 = args[2];
		String num2 = args[3];

		// Add verification of inputs //

		switch (numType.toLowerCase()) {
		case "int":

			break;
		case "float":

			break;
		default:


			break;
		}

		System.out.println("");
	}

	private static void __logger(String message, boolean isError) {
		System.out.println(String.format("%s - %s", isError ? "ERROR" : "INFO", message));
	}

	private static void logError(String message) {
		__logger(message, true);
	}

	private static void logInfo(String message) {
		__logger(message, false);
	}
}