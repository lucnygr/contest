package at.coon_and_friends_2020.ue5;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;

import java.util.Locale;
import java.util.Scanner;

public class Location extends BaseObjectClass {

    public int absoluteTimestamp;

    public int timestampOffset;

    public double latitude;

    public double longitude;

    public double altitude;

    public double x;

    public double y;

    public double z;

    public Location(int startTimestamp, int timestampOffset, double latitude, double longitude, double altitude) {
        this.timestampOffset = timestampOffset;
        this.absoluteTimestamp = startTimestamp + timestampOffset;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;

        this.x = this.getX();
        this.y = this.getY();
        this.z = this.getZ();
    }

    public Location(InputLinesHolder holder, int startTimestamp) {
        Scanner scanner = holder.getNextLineAsScanner(",");
        scanner.useLocale(Locale.ENGLISH);
        this.timestampOffset = scanner.nextInt();
        this.absoluteTimestamp = startTimestamp + this.timestampOffset;
        this.latitude = scanner.nextDouble();
        this.longitude = scanner.nextDouble();
        this.altitude = scanner.nextDouble();

        this.x = this.getX();
        this.y = this.getY();
        this.z = this.getZ();
    }

    public double getDistance(Location location) {
        return Math.sqrt(Math.pow(Math.abs(this.x - location.x), 2) +Math.pow(Math.abs(this.y - location.y), 2) + Math.pow(Math.abs(this.z - location.z), 2));
    }

    public boolean isTransmittable(Location location, double maxDistance) {
        if(this.altitude < 6000 || location.altitude < 6000) {
            return false;
        }
        double distance = this.getDistance(location);
        if(distance <= 1000) {
            return false;
        }
        if(distance >= maxDistance) {
            return false;
        }
        return true;
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
