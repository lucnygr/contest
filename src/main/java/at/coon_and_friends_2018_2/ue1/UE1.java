package at.coon_and_friends_2018_2.ue1;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE1 extends BaseMainClass {

	public static final String INPUT_FILE_ENDING = ".in";

	public static void main(String[] args) throws Exception {
		String year = "18_2";
		String level = "1";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 3; nr <= 3; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
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
		Plan plan = new Plan(inputLinesHolder);

		if (plan.heights.isEmpty()) {
			outputStringBuilder.append("0");
		} else {
			Set<Integer> heights = new HashSet<>(plan.heights);
			heights.stream().filter(h -> h > 0).sorted().forEachOrdered(h -> outputStringBuilder.append(h).append(" "));
			outputStringBuilder.deleteCharAt(outputStringBuilder.length()-1);
		}
		
		
	}
}
