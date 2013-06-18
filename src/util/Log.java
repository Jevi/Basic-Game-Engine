package util;

public class Log {

	public static void print(DebugLevel debugLevel, String message) {
		int depth = 4;
		System.out.print(String.format("[%-5s | %-25s | %-20s]\t%s", debugLevel, getClassName(depth), getMethodName(depth), message));
	}

	public static void println(DebugLevel debugLevel, String message) {
		print(debugLevel, message + "\n");
	}

	public static String getMethodName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[depth].getMethodName();
	}

	public static String getClassName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[depth].getClassName();
	}
}
