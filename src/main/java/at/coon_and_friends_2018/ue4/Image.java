package at.coon_and_friends_2018.ue4;

import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Image extends BaseObjectClass {

	int timestamp;

	int rowcount;

	int colcount;

	int[][] imageArray;

	String asteroidFootprint;

	boolean usedInAsteroidSet = false;

	List<AsteroidImage> rotatedAsteroidImages = Lists.newArrayList();

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
			for (int i = 0; i < 4; i++) {
				this.rotatedAsteroidImages.add(new AsteroidImage(imageArray, i));
			}

			this.rotatedAsteroidImages.sort((i1, i2) -> i1.asteroidFootprint.compareTo(i2.asteroidFootprint));
			this.extractAsteroidFootprint();
		}
	}

	public String getAlignedImageFootprint() {
		return this.rotatedAsteroidImages.get(0).asteroidFootprint;
	}

	public int getAlignedImageRotation() {
		return this.rotatedAsteroidImages.get(0).rotation;
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
