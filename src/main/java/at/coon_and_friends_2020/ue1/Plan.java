package at.coon_and_friends_2020.ue1;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plan extends BaseObjectClass {

	public int rows;

	public int columns;

	public long[][] grid;
	
	public List<Long> gridFlat = new ArrayList<>();

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();
		
		grid = new long[rows][columns];

		for (int x = 0; x < this.rows; x++) {
			scanner = holder.getNextLineAsScanner();
			for (int y = 0; y < this.columns; y++) {
				long height = scanner.nextLong();
				this.grid[x][y] = height;
				gridFlat.add(height);
			}

		}
	}
}
