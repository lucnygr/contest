package at.coon_and_friends.foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ParserUtil {

	private ParserUtil() {
	}

	public static List<Object> parse(String line, Class<?>... types) {
		List<Object> result = new ArrayList<>();

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");

		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (Integer.class.equals(type)) {
				result.add(scanner.nextInt());
			} else if (Double.class.equals(type)) {
				result.add(scanner.nextDouble());
			} else if (String.class.equals(type)) {
				result.add(scanner.next());
			} else if (Long.class.equals(type)) {
				result.add(scanner.nextLong());
			}
		}
		scanner.close();

		return result;
	}

	public static List<Integer> parseForInt(String line) {
		List<Integer> result = new ArrayList<>();

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");

		while (scanner.hasNext()) {
			result.add(scanner.nextInt());
		}
		scanner.close();

		return result;
	}

	public static List<Long> parseForLong(String line) {
		List<Long> result = new ArrayList<>();

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");

		while (scanner.hasNext()) {
			result.add(scanner.nextLong());
		}
		scanner.close();

		return result;
	}

	public static List<Double> parseForDouble(String line) {
		List<Double> result = new ArrayList<>();

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");

		while (scanner.hasNext()) {
			result.add(scanner.nextDouble());
		}
		scanner.close();

		return result;
	}

	public static List<String> parseForString(String line) {
		List<String> result = new ArrayList<>();

		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");

		while (scanner.hasNext()) {
			result.add(scanner.next());
		}
		scanner.close();

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(List<Object> list, int index) {
		return (T) list.get(index);
	}
}
