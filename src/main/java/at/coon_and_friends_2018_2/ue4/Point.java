package at.coon_and_friends_2018_2.ue4;

import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Point extends BaseObjectClass {

	public int row;

	public int col;

	public Point() {
	}

	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;

		if (this.row == point.row && this.col == point.col) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.row * 37 + this.col;
	}

	@Override
	public String toString() {
		return "(" + this.row + " " + this.col + ")";
	}
	
	public void writeTo(StringBuilder sb) {
		sb.append(this.row).append(" ").append(this.col);
	}

}
