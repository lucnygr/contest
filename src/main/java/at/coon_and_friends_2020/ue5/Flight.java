package at.coon_and_friends_2020.ue5;

import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;
import com.sun.tools.attach.AgentInitializationException;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Flight {

    public int flightId;

    public int timestamp;

    public String start;

    public String destination;

    public int takeoff;

    public List<Location> points;

    public TreeMap<Integer, Location> calculatedPoints;

    public Flight(InputLinesHolder holder, int flightId) {
        this.flightId = flightId;
        //scanner.useLocale(Locale.ENGLISH);
        this.start = holder.getNextLineAsString();
        this.destination = holder.getNextLineAsString();
        this.timestamp = holder.getNextLineAsInt();

        int numberOfCoordinates = holder.getNextLineAsInt();

        List<Location> points = new ArrayList<>();
        for(int i = 0;i<numberOfCoordinates;i++) {
            Location location = new Location(holder, this.timestamp);
            points.add(location);
        }
        points.sort(Comparator.comparing(f -> f.timestampOffset));

        this.points = points;

        Logger.debug("Interpolate Flights {}", this.flightId);

        Iterator<Location> locationIt = this.points.iterator();
        Map<Integer, Location> generatedLocations = new HashMap<>();
        Location before = locationIt.next();
        Location after = null;
        while(locationIt.hasNext()) {
            after = locationIt.next();

            UnivariateFunction interpolateLatitude = this.createInterpolator(before.timestampOffset, after.timestampOffset, before.latitude, after.latitude);
            UnivariateFunction interpolateLongitude = this.createInterpolator(before.timestampOffset, after.timestampOffset, before.longitude, after.longitude);
            UnivariateFunction interpolateAltitude = this.createInterpolator(before.timestampOffset, after.timestampOffset, before.altitude, after.altitude);

            generatedLocations.put(before.absoluteTimestamp, before);
            for (int i = before.timestampOffset + 1; i < after.timestampOffset; i++) {
                double interpolatedLatitude = interpolateLatitude.value( i);
                double interpolatedLongitude = interpolateLongitude.value(i);
                double interpolatedAltitude = interpolateAltitude.value(i);
                Location location = new Location(this.timestamp, i, interpolatedLatitude, interpolatedLongitude, interpolatedAltitude);
                generatedLocations.put(location.absoluteTimestamp, location);
            }
            before = after;
        }

        generatedLocations.put(after.absoluteTimestamp, after);

        generatedLocations = generatedLocations.entrySet().stream().filter(e -> e.getValue().altitude > 6000).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        TreeMap<Integer, Location> sortedGeneratedLocations = new TreeMap<>();
        sortedGeneratedLocations.putAll(generatedLocations);

        this.calculatedPoints = sortedGeneratedLocations;
    }

    public UnivariateFunction createInterpolator (double beforeTimestamp, double afterTimestamp, double beforeValue, double afterValue) {
        if(beforeValue == afterValue) {
            return v -> beforeValue;
        }
        return new LinearInterpolator().interpolate(new double[]{beforeTimestamp, afterTimestamp}, new double[]{beforeValue, afterValue});
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
