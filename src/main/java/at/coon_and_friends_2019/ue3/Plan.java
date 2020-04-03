package at.coon_and_friends_2019.ue3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Plan extends BaseObjectClass {

	public int rows;

	public int columns;

	public Cell[][] grid;

	List<Cell> cells = new ArrayList<>();
	
	List<Country> countries = new ArrayList<>();

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();

		grid = new Cell[columns][rows];

		for (int y = 0; y < this.rows; y++) {
			scanner = holder.getNextLineAsScanner();
			for (int x = 0; x < this.columns; x++) {
				Cell cell = new Cell(this, x, y, scanner);
				this.cells.add(cell);
				this.grid[x][y] = cell;
			}
		}
		
		Map<Long, List<Cell>> cellsPerCountry = cells.stream().collect(Collectors.groupingBy(Cell::getCountryId));
		for(Entry<Long, List<Cell>> entry : cellsPerCountry.entrySet()) {
			Country country = new Country(entry.getKey(), entry.getValue());
			this.countries.add(country);
		}
	}


}
