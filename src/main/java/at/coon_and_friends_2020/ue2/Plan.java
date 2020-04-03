package at.coon_and_friends_2020.ue2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import at.coon_and_friends_2020.ue2.Cell;
import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Plan extends at.coon_and_friends.foundation.Plan<at.coon_and_friends_2020.ue2.Cell> {

	public List<Long> gridFlat = new ArrayList<>();

	List<at.coon_and_friends_2020.ue2.Cell> cells = new ArrayList<>();

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();

		this.grid = new at.coon_and_friends_2020.ue2.Cell[columns][rows];

		for (int y = 0; y < this.rows; y++) {
			scanner = holder.getNextLineAsScanner();
			for (int x = 0; x < this.columns; x++) {
				at.coon_and_friends_2020.ue2.Cell result = new at.coon_and_friends_2020.ue2.Cell(x, y, scanner);
				this.grid[x][y] = result;
				this.cells.add(result);
			}
		}
	}

	public boolean isBorderCell(at.coon_and_friends_2020.ue2.Cell cell) {
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
