package at.coon_and_friends_2018.ue2;

import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Image extends BaseObjectClass {

	int timestamp;

	int rowcount;

	int colcount;

	int[][] imageArray;

	String asteroidFootprint;

	public Image(InputLinesHolder holder) {
		Scanner lineScanner = holder.getNextLineAsScanner();
		timestamp = lineScanner.nextInt();
		rowcount = lineScanner.nextInt();
		colcount = lineScanner.nextInt();

		imageArray = new int[colcount][rowcount];

		for (int y = 0; y < rowcount; y++) {
			Scanner nextLineScanner = holder.getNextLineAsScanner();

			for (int x = 0; x < colcount; x++) {
				imageArray[x][y] = nextLineScanner.nextInt();
			}
		}

		if (this.hasAsteroid()) {
			this.extractAsteroid();
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

	public void extractAsteroid() {
		int minX = colcount - 1;
		int minY = rowcount - 1;
		int maxX = 0;
		int maxY = 0;

		for (int x = 0; x < colcount; x++) {
			for (int y = 0; y < rowcount; y++) {
				if (hasSpot(x, y)) {
					minX = Math.min(minX, x);
					minY = Math.min(minY, y);
					maxX = Math.max(maxX, x);
					maxY = Math.max(maxY, y);
				}
			}
		}

		String asteroidFootprint = "";
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (hasSpot(x, y)) {
					asteroidFootprint += "1";
				} else {
					asteroidFootprint += "0";
				}
			}
		}
		this.asteroidFootprint = asteroidFootprint;
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
