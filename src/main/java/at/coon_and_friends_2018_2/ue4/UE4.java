package at.coon_and_friends_2018_2.ue4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE4 extends BaseMainClass {

	public static final String INPUT_FILE_ENDING = ".in";

	public static void main(String[] args) throws Exception {
		String year = "18_2";
		String level = "4";
		String inputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "D:\\Dropbox\\ccc (1)\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 3; nr++) {
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
		Plan plan = new Plan(inputLinesHolder);

		List<Building> foundBuildings = new ArrayList<>();

		for (int row = 0; row < plan.rows; row++) {
			for (int col = 0; col < plan.columns; col++) {
				Cell currentCell = plan.cells[row][col];
				if (currentCell.height == 0) {
					continue;
				}

				if (currentCell.used) {
					continue;
				}

				int buildingRow = row;
				int buildingCol = col;
				while (plan.cells[row][buildingCol + 1].height == currentCell.height) {
					buildingCol++;
				}
				while (plan.cells[buildingRow + 1][col].height == currentCell.height) {
					buildingRow++;
				}

				Building building = new Building(row, col, buildingRow, buildingCol);
				plan.markBuilding(building);
				
				foundBuildings.add(building);
			}
		}

		List<Building> hotspotBuildings = foundBuildings.stream().filter(b -> b.isHotspot()).sorted()
				.collect(Collectors.toList());

		for (int index = 0; index < hotspotBuildings.size(); index++) {
			Building currentBuilder = hotspotBuildings.get(index);
			
			System.out.println(currentBuilder + ", is hotspot: " + currentBuilder.isHotspot());			
			
			outputStringBuilder.append(index).append(" ");
			currentBuilder.center.writeTo(outputStringBuilder);
			if (index < hotspotBuildings.size() - 1) {
				outputStringBuilder.append(" ");
			}
		}
	}

}
