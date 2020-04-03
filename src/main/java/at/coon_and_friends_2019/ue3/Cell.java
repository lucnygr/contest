package at.coon_and_friends_2019.ue3;

import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Cell extends BaseObjectClass implements Comparable<Cell> {

	public int x;

	public int y;

	public long height;

	long countryId;

	Plan plan;
	
	double dinstanceToCenterOfMass;
	
	public Cell(Plan plan, int x, int y, Scanner scanner) {
		this.plan = plan;
		this.x = x;
		this.y = y;
		this.height = scanner.nextLong();
		countryId = scanner.nextLong();
	}
	
	public Long getCountryId() {
		return this.countryId;
	}
	
	public Double getDistanceToCenterOfMass() {
		return this.dinstanceToCenterOfMass;
	}
	
	public boolean isBorderCell() {
		try {
			Cell neighbor = plan.grid[this.x][this.y - 1];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x][this.y + 1];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x - 1][this.y];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x + 1][this.y];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			return true;
		}

		return false;
	}
	
	public void calcDinstanceToCenterOfMass(int x, int y) {
		this.dinstanceToCenterOfMass = Math.sqrt(Math.abs(this.x - x) * Math.abs(this.x - x) + Math.abs(this.y - y) * Math.abs(this.y - y));
	}
	
	@Override
	public int compareTo(Cell o) {
		int result = this.y - o.y;
		if(result != 0) {
			return result;
		}
		return this.x- o.x;
	}
	
	public void printToStringBuilder(StringBuilder sb) {
		sb.append(this.x).append(" ").append(this.y);
	}

}
