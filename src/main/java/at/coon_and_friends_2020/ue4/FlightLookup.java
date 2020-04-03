package at.coon_and_friends_2020.ue4;

import at.coon_and_friends.foundation.InputLinesHolder;

import java.util.Scanner;

public class FlightLookup {

    public int flightId;

    public int timestamp;

    public FlightLookup(InputLinesHolder holder) {
        Scanner scanner = holder.getNextLineAsScanner(" ");
        flightId = scanner.nextInt();
        timestamp = scanner.nextInt();
    }
}
