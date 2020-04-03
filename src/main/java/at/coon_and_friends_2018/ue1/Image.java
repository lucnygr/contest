package at.coon_and_friends_2018.ue1;

import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Image extends BaseObjectClass {

	int timestamp;

	int rowcount;

	int colcount;

	int[][] imageArray;

	public Image(InputLinesHolder holder) {
		Scanner lineScanner = holder.getNextLineAsScanner();
		timestamp = lineScanner.nextInt();
		rowcount = lineScanner.nextInt();
		colcount = lineScanner.nextInt();

		imageArray = new int[rowcount][colcount];

		for (int i = 0; i < rowcount; i++) {
			Scanner nextLineScanner = holder.getNextLineAsScanner();

			for (int j = 0; j < colcount; j++) {
				imageArray[i][j] = nextLineScanner.nextInt();
			}
		}

		Logger.info(this.getClass() + ": " + this.toString());
	}

	public boolean hasSpot(int x, int y) {
		if (imageArray[x][y] > 0) {
			return true;
		}
		return false;
	}

	public boolean hasAsteroid() {
		for (int x = 0; x < colcount; x++) {
			for (int y = 0; y < rowcount; y++) {
				if (hasSpot(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	// public boolean hasNeighborWithSpot(int x, int y) {
	// for (int i = Math.min(0, x - 1); i <= Math.max(colcount, x + 1); i++) {
	// for (int j = Math.min(0, y - 1); j <= Math.max(rowcount, y + 1); y++) {
	// if (i == x && j == y) {
	// // Überspringe
	// continue;
	// }
	//
	// if (hasSpot(i, j)) {
	// return true;
	// }
	// }
	//
	// }
	// return false;
	// }
}
