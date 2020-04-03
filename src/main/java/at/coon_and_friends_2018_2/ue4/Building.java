package at.coon_and_friends_2018_2.ue4;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Building extends BaseObjectClass implements Comparable<Building> {

	public int row1;

	public int col1;

	public int row2;

	public int col2;

	public Point center;

	public Building(int row1, int col1, int row2, int col2) {
		this.row1 = row1;
		this.col1 = col1;
		this.row2 = row2;
		this.col2 = col2;

		this.center = this.getCenter();
	}

	public boolean isHotspot() {
		if (Math.abs(this.row1 - this.row2) < 3) {
			return false;
		}
		if (Math.abs(this.col1 - this.col2) < 3) {
			return false;
		}
		return true;
	}

	public Point getCenter() {
		int deltaR = this.row2 - this.row1;
		int deltaC = this.col2 - this.col1;

		double rowC = this.row1 + deltaR * 0.5 + 0.5;
		double colC = this.col1 + deltaC * 0.5 + 0.5;

		if (rowC % 1 == 0) {
			rowC -= 1;
		}
		if (colC % 1 == 0) {
			colC -= 1;
		}

		return new Point((int) Math.floor(rowC), (int) Math.floor(colC));
	}

	@Override
	public int compareTo(Building o) {
		int result = this.center.row - o.center.row;
		if (result != 0) {
			return result;
		}
		return this.center.col - o.center.col;
	}

}
