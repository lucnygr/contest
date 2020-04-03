package at.coon_and_friends_2019.ue1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE1 extends BaseMainClass {
	
	public static final String INPUT_FILE_ENDING = ".in";
	
	public static final String OUTPUT_FILE_ENDING = ".out";

	public static void main(String[] args) throws Exception {
		String year = "19";
		String level = "1";
		String inputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 5; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
			String[] lines = content.split("\n");
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + OUTPUT_FILE_ENDING,
					outputStringBuilder.toString());
		}
	}

	private static void process(InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) {
		Plan plan = new Plan(inputLinesHolder);
		
		long min = plan.gridFlat.stream().min(Long::compare).get();
		long max = plan.gridFlat.stream().max(Long::compare).get();
		double avg = Math.floor(((double) plan.gridFlat.stream().reduce(0L, Long::sum)) / plan.gridFlat.size());

	//	System.out.println(String.format("%s %s %", min, max, avg));
	
		outputStringBuilder.append(min).append(" ").append(max).append(" ").append(new BigDecimal(avg).toPlainString());
		
	}
}
