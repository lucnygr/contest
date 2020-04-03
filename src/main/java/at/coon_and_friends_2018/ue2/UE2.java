package at.coon_and_friends_2018.ue2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE2 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "18";
		String level = "2";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 4; nr++) {
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

		List<Image> resultList = imageList.stream().filter(image -> image.hasAsteroid()).collect(Collectors.toList());

		Map<String, List<Image>> resultMap = resultList.stream()
				.collect(Collectors.groupingBy(i -> i.asteroidFootprint, Collectors.toList()));

		// Sortiere alle Entries einer Liste
		for (Entry<String, List<Image>> entry : resultMap.entrySet()) {
			entry.getValue().sort((i1, i2) -> i1.timestamp - i2.timestamp);
		}

		List<List<Image>> groupedImages = new ArrayList<>(resultMap.values());
		groupedImages.sort((l1, l2) -> l1.get(0).timestamp - l2.get(0).timestamp);

		for (List<Image> images : groupedImages) {
			outputStringBuilder.append(images.get(0).timestamp).append(" ")
					.append(images.get(images.size() - 1).timestamp).append(" ").append(images.size()).append("\n");
		}
	}
}
