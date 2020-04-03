package at.coon_and_friends_2020.ue1;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;
import org.pmw.tinylog.Logger;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

import static at.coon_and_friends.foundation.Constants.*;

public class UE1 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "20";
		String level = "1";
		String inputDirectory = ROOT_DIRECTORY + year + "\\" + level + "\\in\\";
		String outputDirectory = ROOT_DIRECTORY + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 5; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
			String[] lines = content.split(LINEBREAK);
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(Paths.get(outputDirectory + "\\image" + nr + ".png"), inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + OUTPUT_FILE_ENDING,
					outputStringBuilder.toString());
		}
	}

	private static void process(Path imagePath, InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) {
		Plan plan = new Plan(inputLinesHolder);
		
		int minTimestamp = plan.locations.stream().map(l -> l.timestamp).min(Integer::compare).get();
		int maxTimestamp = plan.locations.stream().map(l -> l.timestamp).max(Integer::compare).get();

		double minLatitude = plan.locations.stream().map(l -> l.point.x).min(Double::compare).get();
		double maxLatitude = plan.locations.stream().map(l -> l.point.x).max(Double::compare).get();

		double minLongitude = plan.locations.stream().map(l -> l.point.y).min(Double::compare).get();
		double maxLongitude = plan.locations.stream().map(l -> l.point.y).max(Double::compare).get();

		float maxAltitude = plan.locations.stream().map(l -> l.altitude).max(Float::compare).get();
	
		outputStringBuilder.append(minTimestamp).append(" ").append(maxTimestamp).append("\n")
				.append(minLatitude).append(" ").append(maxLatitude).append("\n")
				.append(minLongitude).append(" ").append(maxLongitude).append("\n")
				.append(maxAltitude);
		
	}
}
