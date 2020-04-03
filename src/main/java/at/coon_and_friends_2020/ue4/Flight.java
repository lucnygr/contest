package at.coon_and_friends_2020.ue4;

import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;

import java.util.*;

public class Flight {

    public int timestamp;

    public String start;

    public String destination;

    public int takeoff;

    public List<Location> points;

    public Flight(InputLinesHolder holder) {
        //scanner.useLocale(Locale.ENGLISH);
        this.start = holder.getNextLineAsString();
        this.destination = holder.getNextLineAsString();
        this.timestamp = holder.getNextLineAsInt();

        int numberOfCoordinates = holder.getNextLineAsInt();

        List<Location> points = new ArrayList<>();
        for(int i = 0;i<numberOfCoordinates;i++) {
            Location location = new Location(holder);
            points.add(location);
        }
        points.sort(Comparator.comparing(f -> f.timestampOffset));

        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return takeoff == flight.takeoff &&
                Objects.equals(start, flight.start) &&
                Objects.equals(destination, flight.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, destination, takeoff);
    }
}
