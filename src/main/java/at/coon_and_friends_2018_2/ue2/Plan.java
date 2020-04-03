package at.coon_and_friends_2018_2.ue2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Plan extends BaseObjectClass {

	public int rows;

	public int columns;

	public List<Integer> heights = new ArrayList<>();

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();

		for (int i = 0; i < this.rows; i++) {
			scanner = holder.getNextLineAsScanner();
			for (int y = 0; y < this.columns; y++) {
				int height = scanner.nextInt();
				if (height > 0) {
					heights.add(height);
				}
			}

		}
	}
}
