package at.coon_and_friends.foundation.geom;

import at.coon_and_friends.foundation.InputLinesHolder;

import java.awt.geom.Point2D;
import java.util.Scanner;

public class DoublePoint extends Point2D.Double {

    public DoublePoint(double x, double y) {
        super(x, y);
    }

    public DoublePoint(DoublePoint point) {
        super(point.x, point.y);
    }

    public DoublePoint(InputLinesHolder holder) {
        this(holder.getNextLineAsScanner());
    }

    public DoublePoint(Scanner scanner) {
        this.x = scanner.nextDouble();
        this.y = scanner.nextDouble();
    }
}
