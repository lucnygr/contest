package at.coon_and_friends_2018.ue1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE1 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "18";
		String level = "1";
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
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + ".out",
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
		resultList.sort((i1, i2) -> i1.timestamp - i2.timestamp);

		for (Image image : resultList) {
			outputStringBuilder.append(image.timestamp).append("\n");
		}
	}
}
