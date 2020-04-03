package at.coon_and_friends.foundation;

import java.util.Iterator;
import java.util.Scanner;

import com.google.common.collect.Lists;

public class InputLinesHolder {

	Iterator<String> inputLinesIterator;

	public InputLinesHolder(String[] inputLines) {
		this.inputLinesIterator = Lists.newArrayList(inputLines).iterator();
	}

	public String getNextLineAsString() {
		String line = inputLinesIterator.next();
		return line;
	}

	public Scanner getNextLineAsScanner() {
		String line = inputLinesIterator.next();
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");
		return scanner;
	}

	public int getNextLineAsInt() {
		String line = inputLinesIterator.next();
		return Integer.parseInt(line);
	}

}
