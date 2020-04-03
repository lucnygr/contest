package at.coon_and_friends_2020.ue4;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;

import java.util.Locale;
import java.util.Scanner;

public class Location extends BaseObjectClass {

    public int timestampOffset;

    public double latitude;

    public double longitude;

    public double altitude;

    public Location(InputLinesHolder holder) {
        Scanner scanner = holder.getNextLineAsScanner(",");
        scanner.useLocale(Locale.ENGLISH);
        this.timestampOffset = scanner.nextInt();
        this.latitude = scanner.nextDouble();
        this.longitude = scanner.nextDouble();
        this.altitude = scanner.nextDouble();
    }

    public double getX() {
        double radius = 6371 * 1000 + this.altitude;

        double x = radius * Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(this.longitude));
        // x = R * cos(lat) * cos(lon)
        return x;
    }

    public double getY() {
        double radius = 6371 * 1000 + this.altitude;

        double y = radius * Math.cos(Math.toRadians(this.latitude)) * Math.sin(Math.toRadians(this.longitude));
        // y = R * cos(lat) * sin(lon)
        return y;
    }

    public double getZ() {
        double radius = 6371 * 1000 + this.altitude;

        double z = radius * Math.sin(Math.toRadians(this.latitude));
        //z = R *sin(lat)
        return z;
    }
}
