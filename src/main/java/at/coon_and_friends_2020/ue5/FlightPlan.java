package at.coon_and_friends_2020.ue5;

import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static at.coon_and_friends.foundation.Constants.LINEBREAK;
import static at.coon_and_friends.foundation.Constants.ROOT_DIRECTORY;

public class FlightPlan {

    public double transferRange;

    public List<Flight> flights;

    public FlightPlan(InputLinesHolder holder) throws IOException {
        this.transferRange = holder.getNextLineAsDouble();
        int nr = holder.getNextLineAsInt();

        List<Flight> flights = new ArrayList<>();
        for(int i = 0;i<nr;i++) {
            int flightId = holder.getNextLineAsInt();

            String content = FileUtil.readFile(ROOT_DIRECTORY + "20" + "\\" + "5" + "\\in\\usedFlights\\" + flightId + ".csv");
            Flight flight = new Flight(new InputLinesHolder(content.split(LINEBREAK)), flightId);
            flights.add(flight);
        }
        this.flights = flights.stream().filter(f -> f.calculatedPoints.size() > 0).sorted(Comparator.comparing(f -> f.flightId)).collect(Collectors.toList());
    }
}
