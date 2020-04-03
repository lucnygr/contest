package at.coon_and_friends_2018.ue4;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.MathUtil;

public class AsteroidImage extends BaseObjectClass {

	int rowcount;

	int colcount;

	int[][] imageArray;

	String asteroidFootprint;

	int rotation;

	public AsteroidImage(int[][] imageArray, int rotation) {
		this.rotation = rotation;
		this.imageArray = imageArray;
		this.colcount = imageArray.length;
		this.rowcount = imageArray[0].length;

		if (this.hasAsteroid()) {
			for (int i = 0; i < rotation; i++) {
				imageArray = MathUtil.rotateMatrixClockwise(imageArray);
			}

			this.imageArray = imageArray;
			this.colcount = imageArray.length;
			this.rowcount = imageArray[0].length;

			this.extractAsteroidFootprint();
		}
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

	public void extractAsteroidFootprint() {
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
}
