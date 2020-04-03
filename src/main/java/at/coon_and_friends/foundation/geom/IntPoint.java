package at.coon_and_friends.foundation.geom;

import at.coon_and_friends.foundation.InputLinesHolder;

import java.awt.*;
import java.util.Scanner;

public class IntPoint extends Point {

    public IntPoint(int x, int y) {
        super(x, y);
    }

    public IntPoint(IntPoint point) {
        super(point.x, point.y);
    }

    public IntPoint(InputLinesHolder holder) {
        this(holder.getNextLineAsScanner());
    }

    public IntPoint(Scanner scanner) {
        this.x = scanner.nextInt();
        this.y = scanner.nextInt();
    }
}
