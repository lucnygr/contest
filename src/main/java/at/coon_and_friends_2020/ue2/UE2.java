package at.coon_and_friends_2020.ue2;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;
import org.pmw.tinylog.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static at.coon_and_friends.foundation.Constants.*;

public class UE2 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "20";
		String level = "2";
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

		Set<Flight> distinctFlights = new HashSet<>(plan.flights);
		Map<Object, Long> groupedFlights = distinctFlights.stream().collect(Collectors.groupingBy(f -> f.start + " " + f.destination, Collectors.counting()));

		SortedMap<Object, Long> sortedFlights = new TreeMap<>((o1, o2) -> o1.toString().compareTo(o2.toString()));
		sortedFlights.putAll(groupedFlights);

		for(Map.Entry<Object, Long> entry : sortedFlights.entrySet()) {
			outputStringBuilder.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
		}
	}
}
