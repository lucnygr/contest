package at.coon_and_friends_2019.ue2;

import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Cell extends BaseObjectClass implements Comparable<Cell> {

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
