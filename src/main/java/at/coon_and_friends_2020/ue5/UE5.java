package at.coon_and_friends_2020.ue5;

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

public class UE5 extends BaseMainClass {

	public static void main(String[] args) throws Exception {
		String year = "20";
		String level = "5";
		String inputDirectory = ROOT_DIRECTORY + year + "\\" + level + "\\in\\";
		String outputDirectory = ROOT_DIRECTORY + year + "\\" + level + "\\out\\";

		for (int nr = 4; nr <= 4; nr++) {
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
		FlightPlan plan = new FlightPlan(inputLinesHolder);

		for(Flight flightA : plan.flights) {
			for(Flight flightB : plan.flights) {
				if(flightA == flightB) continue;
				if(flightA.destination.equals(flightB.destination)) continue;
				if(flightA.calculatedPoints.firstKey() > flightB.calculatedPoints.lastKey() + 3600) continue;
				if(flightA.calculatedPoints.lastKey() < flightB.calculatedPoints.firstKey()) continue;

				Logger.debug("Checking Flights {} and {}", flightA.flightId, flightB.flightId);

				for(int i = 0;i<60 *60;i++) {
					List<Integer> meetupTimestamps = new ArrayList<>();

					for(Map.Entry<Integer, Location> locationAEntry : flightA.calculatedPoints.entrySet()) {
						if(!flightB.calculatedPoints.containsKey(locationAEntry.getKey() - i)) {
							continue;
						}

						Location locationB = flightB.calculatedPoints.get(locationAEntry.getKey() - i);
						double distance = locationAEntry.getValue().getDistance(locationB);
						if(distance <= 1000) {
							continue;
						}
						if(distance >= plan.transferRange) {
							int skipseconds = (int) (distance - plan.transferRange) / 300;
							i += skipseconds;
							Logger.debug("Skipping {}", skipseconds);
							continue;
						}

						/*
						if(!locationAEntry.getValue().isTransmittable(locationB, plan.transferRange)) {
							continue;
						}
						*/

						meetupTimestamps.add(locationAEntry.getKey());
					}

					meetupTimestamps.sort(Comparator.naturalOrder());
					Iterator<Integer> meetupTimestampsIt = meetupTimestamps.iterator();
					if(meetupTimestamps.size() > 0) {

						outputStringBuilder.append(flightA.flightId).append(" ").append(flightB.flightId).append(" ").append(i).append(" ");

						/*
						for(int index = 0;index< meetupTimestamps.size();index++) {
							outputStringBuilder.append(meetupTimestamps.get(index)).append(" ");
						}
						*/

						Integer lastTimestamp = null;
						boolean inSequnce = false;
						for(int index = 0;index< meetupTimestamps.size();index++) {
							if(lastTimestamp == null) {
								outputStringBuilder.append(meetupTimestamps.get(index));
							}
							else if(lastTimestamp + 1 == meetupTimestamps.get(index)) {
								inSequnce = true;
							}
							else {
								if (inSequnce){
									inSequnce = false;
									outputStringBuilder.append("-").append(lastTimestamp).append(" ")
											.append(meetupTimestamps.get(index));
								}
								else {
									outputStringBuilder.append(" ")
											.append(meetupTimestamps.get(index));
								}
							}
							lastTimestamp = meetupTimestamps.get(index);
						}
						if(inSequnce) {
							outputStringBuilder.append("-").append(lastTimestamp);
						}

						outputStringBuilder.append("\n");
					}
				}
			}

		}



/*

		for(int i = 0;i<nr;i++) {


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



			outputStringBuilder.append(interpolatedLatitude).append(" ").append(interpolatedLongitude).append(" ").append(interpolatedAltitude).append(LINEBREAK);
		}
		*/

	}


}
