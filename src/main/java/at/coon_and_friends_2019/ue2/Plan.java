package at.coon_and_friends_2019.ue2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Plan extends BaseObjectClass {

	public int rows;

	public int columns;

	public Cell[][] grid;

	public List<Long> gridFlat = new ArrayList<>();

	List<Cell> cells = new ArrayList<>();

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();

		grid = new Cell[columns][rows];

		for (int y = 0; y < this.rows; y++) {
			scanner = holder.getNextLineAsScanner();
			for (int x = 0; x < this.columns; x++) {
				Cell cell = new Cell(x, y, scanner);
				this.cells.add(cell);

				this.grid[x][y] = cell;
			}

		}
	}

	public boolean isBorderCell(Cell cell) {
		try {
			Cell neighbor = this.grid[cell.x][cell.y - 1];
			if (neighbor.countryId != cell.countryId) {
				return true;
			}
			neighbor = this.grid[cell.x][cell.y + 1];
			if (neighbor.countryId != cell.countryId) {
				return true;
			}
			neighbor = this.grid[cell.x - 1][cell.y];
			if (neighbor.countryId != cell.countryId) {
				return true;
			}
			neighbor = this.grid[cell.x + 1][cell.y];
			if (neighbor.countryId != cell.countryId) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			return true;
		}

		return false;
	}
}
