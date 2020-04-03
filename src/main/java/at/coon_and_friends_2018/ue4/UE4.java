package at.coon_and_friends_2018.ue4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE4 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "18";
		String level = "4";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 7; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil.readFile(inputDirectory + "lvl" + level + "-" + levelnumber + ".inp");
			String[] lines = content.split("\n");
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + ".txt",
					outputStringBuilder.toString());
		}
	}

	private static void process(InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) {

		Observation observation = new Observation(inputLinesHolder.getNextLineAsScanner());

		List<Image> imageList = new ArrayList<>();
		for (int i = 0; i < observation.imagecount; i++) {
			Image image = new Image(inputLinesHolder);
			imageList.add(image);
		}

		// filtere Bilder ohne Asteroid aus
		List<Image> imagesWithAsteroids = imageList.stream().filter(image -> image.hasAsteroid())
				.collect(Collectors.toList());

		Map<String, List<Image>> imagesWithAsteroidsGroupedByFootprint = imagesWithAsteroids.stream()
				.collect(Collectors.groupingBy(i -> i.getAlignedImageFootprint(), Collectors.toList()));

		// Sortiere alle Entries einer Liste eines Asteroiden mit gleichem
		// Footprint
		for (Entry<String, List<Image>> entry : imagesWithAsteroidsGroupedByFootprint.entrySet()) {
			entry.getValue().sort((i1, i2) -> i1.timestamp - i2.timestamp);
		}

		List<List<Image>> groupedImages = new ArrayList<>(imagesWithAsteroidsGroupedByFootprint.values());
		groupedImages.sort((l1, l2) -> l1.get(0).timestamp - l2.get(0).timestamp);

		List<Asteroid> asteroiden = new ArrayList<>();

		for (List<Image> images : groupedImages) {
			List<Image> currentAsteroidSet = images;
			// if
			// (images.get(0).asteroidFootprint.equals("001100001101001011110111111100001100000010"))
			// {
			// Logger.info("Problemasteroid");
			// for (Image image : images) {
			// Logger.info(image);
			// }
			// }

			for (int h = 0; h < currentAsteroidSet.size(); h++) {
				Image firstAsteroid = currentAsteroidSet.get(h);
				if (firstAsteroid.usedInAsteroidSet) {
					continue;
				}

				for (int i = h + 1; i < currentAsteroidSet.size(); i++) {
					Image nextAsteroid = currentAsteroidSet.get(i);
					if (nextAsteroid.usedInAsteroidSet) {
						continue;
					}

					int interval = nextAsteroid.timestamp - firstAsteroid.timestamp;
					int rotationSpeed = (nextAsteroid.getAlignedImageRotation()
							- firstAsteroid.getAlignedImageRotation() + 4) % 4;

					List<Image> imageGroup = getImageGroup(currentAsteroidSet, firstAsteroid.timestamp, interval,
							firstAsteroid.getAlignedImageRotation(), rotationSpeed, observation.startTimestamp,
							observation.endTimestamp);

					if (imageGroup.size() > 0) {
						for (Image asteroidImage : imageGroup) {
							asteroidImage.usedInAsteroidSet = true;
						}
						Asteroid asteroid = new Asteroid();
						asteroid.images = imageGroup;
						asteroid.interval = interval;
						asteroid.timestampFirstSpot = imageGroup.get(0).timestamp;
						asteroiden.add(asteroid);
					}
				}
			}

			for (Image image : currentAsteroidSet) {
				if (!image.usedInAsteroidSet) {
					Logger.info("übrige asteroiden" + image);
				}
			}
		}

		asteroiden.sort((a1, a2) -> a1.timestampFirstSpot - a2.timestampFirstSpot);

		for (Asteroid asteroid : asteroiden) {
			if (asteroid.images.get(0).asteroidFootprint.equals("001100001101001011110111111100001100000010")) {
				// Logger.info("Gefundenes Set");
				Logger.info(asteroid.interval);
				for (Image image : asteroid.images) {
					Logger.info(image);
				}
			}

			// outputStringBuilder.append(asteroid.images.get(0).timestamp).append("
			// ")
			// .append(asteroid.images.get(asteroid.images.size() -
			// 1).timestamp).append(" ").append(" interval ")
			// .append(asteroid.interval).append(asteroid.images.size()).append("\n");

			outputStringBuilder.append(asteroid.images.get(0).timestamp).append(" ")
					.append(asteroid.images.get(asteroid.images.size() - 1).timestamp).append(" ")
					.append(asteroid.images.size()).append("\n");
		}
	}

	private static List<Image> getImageGroup(List<Image> images, int firstTimestamp, int interval, int startRotation,
			int rotationSpeed, int startobservation, int endobservetion) {
		Logger.info("firstTimestamp " + firstTimestamp + ", interval " + interval + ", startRoatation " + startRotation
				+ ", rotationSpeed " + rotationSpeed);

		ArrayList<Image> tmpList = new ArrayList<>();

		int count = 0;

		for (Image image : images) {
			if (image.usedInAsteroidSet) {
				continue;
			}

			if ((image.timestamp - firstTimestamp) % interval == 0
					&& image.timestamp == firstTimestamp + count * interval
					&& image.getAlignedImageRotation() == (startRotation + count * rotationSpeed) % 4) {
				tmpList.add(image);
				count++;
				// Logger.info(image.timestamp + " tät passen");

			} else {
				// Logger.info(image.timestamp + " passt ned");
			}

		}

		if (tmpList.size() < 4) {
			return new ArrayList<>(0);
		}

		if (tmpList.get(0).timestamp >= startobservation + interval) {
			return new ArrayList<>(0);
		}

		if (tmpList.get(tmpList.size() - 1).timestamp <= endobservetion - interval) {
			return new ArrayList<>(0);
		}

		return tmpList;
	}

}
