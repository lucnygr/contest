package at.coon_and_friends_2018_2.ue4;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Cell extends BaseObjectClass {

	public int row;

	public int col;

	public int height;

	public boolean used;

	public Cell() {
	}

	public Cell(int row, int col, int height) {
		this.row = row;
		this.col = col;
		this.height = height;
	}

}
