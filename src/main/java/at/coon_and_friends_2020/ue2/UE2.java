package at.coon_and_friends_2020.ue2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import at.coon_and_friends_2020.ue2.Cell;
import at.coon_and_friends_2020.ue2.Plan;
import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

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
			String[] lines = content.split("\n");	
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(Paths.get(outputDirectory + "\\image" + nr + ".png"), inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + OUTPUT_FILE_ENDING,
					outputStringBuilder.toString());
		}
	}

	private static void process(Path imagePath, InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) throws IOException {
		Plan plan = new Plan(inputLinesHolder);

		plan.draw(imagePath, 10);
		
		List<Cell> borderCells = plan.cells.stream().filter(c -> plan.isBorderCell(c)).sorted().collect(Collectors.toList());
		borderCells.forEach(c -> Logger.info(c));
		
		Map<Long, Long> borderCellsPerCountry = borderCells.stream().collect(Collectors.groupingBy(Cell::getCountryId, Collectors.counting()));
		borderCellsPerCountry = new TreeMap<>(borderCellsPerCountry);
	
		for(Entry<Long, Long> entry : borderCellsPerCountry.entrySet()) {
			outputStringBuilder.append(entry.getValue()).append("\n");
		}
		
	}
}
