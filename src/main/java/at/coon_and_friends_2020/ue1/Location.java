package at.coon_and_friends_2020.ue1;

import at.coon_and_friends.foundation.InputLinesHolder;
import at.coon_and_friends.foundation.geom.DoublePoint;

import java.util.Locale;
import java.util.Scanner;

public class Location {

    public int timestamp;

    public DoublePoint point;

    public float altitude;

    public Location(InputLinesHolder holder) {
        Scanner scanner = holder.getNextLineAsScanner();
        scanner.useLocale(Locale.ENGLISH);
        this.timestamp = scanner.nextInt();
        this.point = new DoublePoint(scanner);
        this.altitude = scanner.nextFloat();
    }

}
