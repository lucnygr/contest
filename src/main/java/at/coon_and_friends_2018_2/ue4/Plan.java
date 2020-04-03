package at.coon_and_friends_2018_2.ue4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Plan extends BaseObjectClass {

	public int rows;

	public int columns;

	public Cell[][] cells;

	public Plan(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.rows = scanner.nextInt();
		this.columns = scanner.nextInt();
		
		this.cells = new Cell[this.rows][this.columns];

		for (int x = 0; x < this.rows; x++) {
			scanner = holder.getNextLineAsScanner();
			for (int y = 0; y < this.columns; y++) {
				this.cells[x][y] = new Cell(x, y, scanner.nextInt());
			}

		}
	}
	
	public void markBuilding(Building building) {
		for(int row = building.row1; row <= building.row2;row++) {
			for(int col = building.col1; col <= building.col2;col++) {
				this.cells[row][col].used = true;
			}
		}
	}
}
