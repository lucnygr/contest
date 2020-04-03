package at.coon_and_friends_2020.ue2;

import java.awt.*;
import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.Drawable;

public class Cell extends BaseObjectClass implements Comparable<Cell>, Drawable {

	public int x;

	public int y;

	public long height;

	long countryId;

	public Cell(int x, int y, Scanner scanner) {
		this.x = x;
		this.y = y;
		this.height = scanner.nextLong();
		countryId = scanner.nextLong();
	}

	@Override
	public String getDrawString() {
		return "Cell{" +
				"height=" + height +
				",\ncountryId=" + countryId +
				'}';
	}

	@Override
	public Color getCellBackgroundColor(int row, int column) {
		return Color.GREEN;
	}

	public Long getCountryId() {
		return this.countryId;
	}
	
	@Override
	public int compareTo(Cell o) {
		int result = this.y - o.y;
		if(result != 0) {
			return result;
		}
		return this.x- o.x;
	}

}
