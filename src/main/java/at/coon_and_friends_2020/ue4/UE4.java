package at.coon_and_friends_2020.ue4;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static at.coon_and_friends.foundation.Constants.*;

public class UE4 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "20";
		String level = "4";
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

	private static void process(Path imagePath, InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) throws IOException {
		int nr = inputLinesHolder.getNextLineAsInt();

		for(int i = 0;i<nr;i++) {
			FlightLookup lookup = new FlightLookup(inputLinesHolder);

			String content = FileUtil.readFile(ROOT_DIRECTORY + "20" + "\\" + "4" + "\\in\\usedFlights\\" + lookup.flightId + ".csv");
			Flight flight = new Flight(new InputLinesHolder(content.split(LINEBREAK)));

			long lookupTimestampoffset = lookup.timestamp - flight.timestamp;
			Logger.info("Lookup flight {}, Timestamp {}, Timestamp offset {}", lookup.flightId, lookup.timestamp, lookupTimestampoffset);

			Location before = flight.points.stream().filter(p -> p.timestampOffset <= lookupTimestampoffset).max(Comparator.comparing(f -> f.timestampOffset)).get();
			if(before.timestampOffset == lookup.timestamp) {
				outputStringBuilder.append(before.latitude).append(" ").append(before.longitude).append(" ").append(before.altitude).append(LINEBREAK);
				continue;
			}

			Location after = flight.points.stream().filter(p -> p.timestampOffset >= lookupTimestampoffset).min(Comparator.comparing(f -> f.timestampOffset)).get();

			Logger.debug("Before {}", before);
			Logger.debug("Afte {}", after);

			double interpolatedLatitude = interpolate(lookupTimestampoffset, before.timestampOffset, after.timestampOffset, before.latitude, after.latitude);
			double interpolatedLongitude = interpolate(lookupTimestampoffset, before.timestampOffset, after.timestampOffset, before.longitude, after.longitude);
			double interpolatedAltitude = interpolate(lookupTimestampoffset, before.timestampOffset, after.timestampOffset, before.altitude, after.altitude);

			outputStringBuilder.append(interpolatedLatitude).append(" ").append(interpolatedLongitude).append(" ").append(interpolatedAltitude).append(LINEBREAK);
		}
	}

	public static double interpolate(double searchTimestamp, double beforeTimestamp, double afterTimestamp, double beforeValue, double afterValue) {
		if(beforeValue == afterValue) {
			return beforeValue;
		}

		PolynomialSplineFunction function = new LinearInterpolator().interpolate(new double[]{beforeTimestamp, afterTimestamp}, new double[]{beforeValue, afterValue});
		return function.value(searchTimestamp);
	}
}
