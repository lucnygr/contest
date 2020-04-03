package at.coon_and_friends_2019.ue3;

import java.util.Collections;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE3 extends BaseMainClass {
	
	public static final String INPUT_FILE_ENDING = ".in";
	
	public static final String OUTPUT_FILE_ENDING = ".out";

	public static void main(String[] args) throws Exception {
		String year = "19";
		String level = "3";
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
		
		Collections.sort(plan.countries);
		

		for(Country country : plan.countries) {
			country.capital.printToStringBuilder(outputStringBuilder);
			outputStringBuilder.append("\n");
		}
		
	}
}
