package at.coon_and_friends_2018_2.ue3;

import java.util.Set;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE3 extends BaseMainClass {

	public static final String INPUT_FILE_ENDING = ".in";

	public static void main(String[] args) throws Exception {
		String year = "18_2";
		String level = "3";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 2; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil
					.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
			String[] lines = content.split("\r\n");
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + ".out",
					outputStringBuilder.toString());
		}
	}

	private static void process(InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) {
		int nrOfTuples = inputLinesHolder.getNextLineAsInt();

		for (int i = 0; i < nrOfTuples; i++) {
			Line line = new Line(inputLinesHolder);
			Set<Point> pointsOfLine = line.calculatePoints();
			pointsOfLine.stream().forEach(p -> outputStringBuilder.append(p.row).append(" ").append(p.col).append(" "));
			outputStringBuilder.deleteCharAt(outputStringBuilder.length() - 1);
			if (i < nrOfTuples - 1) {
				outputStringBuilder.append("\n");
			}
		}

	}
}
