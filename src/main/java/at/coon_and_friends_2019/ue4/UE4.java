package at.coon_and_friends_2019.ue4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE4 extends BaseMainClass {

	public static final String INPUT_FILE_ENDING = ".in";

	public static final String OUTPUT_FILE_ENDING = ".out";

	public static void main(String[] args) throws Exception {
		String year = "19";
		String level = "4";
		String inputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\out\\";

		for (int nr = 1; nr <= 5; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil
					.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
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

		int anzahl = inputLinesHolder.getNextLineAsInt();
		List<Ray> rays = new ArrayList<>();

		for (int i = 0; i < anzahl; i++) {
			Ray ray = new Ray(inputLinesHolder);
			ray.calcEndpoint(plan.rows, plan.columns);
			rays.add(ray);

			List<Cell> touchedCells = plan.cells.stream()
					.filter(c -> c.hasCollisionWithCell(ray.x, ray.y, ray.endX, ray.endY)).collect(Collectors.toList());

			if (ray.isDominantHorizontal()) {
				touchedCells = touchedCells.stream().sorted((c1, c2) -> {
					int result = (c1.y - c2.y) * ray.getDirectionMultiplierY();
					if (result == 0) {
						result = (c1.x - c2.x) * ray.getDirectionMultiplierX();
					}
					return result;
				}).collect(Collectors.toList());
			} else {
				touchedCells = touchedCells.stream().sorted((c1, c2) -> {
					int result = (c1.x - c2.x) * ray.getDirectionMultiplierX();
					if (result == 0) {
						result = (c1.y - c2.y) * ray.getDirectionMultiplierY();
					}
					return result;
				}).collect(Collectors.toList());
			}

			Iterator<Cell> cellIt = touchedCells.iterator();
			while (cellIt.hasNext()) {
				cellIt.next().printToStringBuilder(outputStringBuilder);
				if (cellIt.hasNext()) {
					outputStringBuilder.append(" ");
				}
			}
			if (i < (anzahl - 1)) {
				outputStringBuilder.append("\n");
			}
		}

	}
}
