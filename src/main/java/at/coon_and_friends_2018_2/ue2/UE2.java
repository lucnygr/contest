package at.coon_and_friends_2018_2.ue2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE2 extends BaseMainClass {

	public static final String INPUT_FILE_ENDING = ".in";

	public static void main(String[] args) throws Exception {
		String year = "18_2";
		String level = "2";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 4; nr++) {
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
			line.calculatePoint(outputStringBuilder);
			if (i < nrOfTuples - 1) {
				outputStringBuilder.append("\n");
			}
		}

	}
}
