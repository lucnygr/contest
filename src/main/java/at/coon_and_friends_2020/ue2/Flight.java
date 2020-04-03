package at.coon_and_friends_2020.ue2;

import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Flight {

    public int timestamp;

    public DoublePoint latitudeLongitude;

    public float altitude;

    public String start;

    public String destination;

    public int takeoff;

    public Flight(InputLinesHolder holder) {
        Scanner scanner = holder.getNextLineAsScanner();
        scanner.useLocale(Locale.ENGLISH);
        this.timestamp = scanner.nextInt();
        this.latitudeLongitude = new DoublePoint(scanner);
        this.altitude = scanner.nextFloat();
        this.start = scanner.next();
        this.destination = scanner.next();
        this.takeoff = scanner.nextInt();
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
