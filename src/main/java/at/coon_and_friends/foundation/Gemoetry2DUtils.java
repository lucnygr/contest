package at.coon_and_friends.foundation;

import java.awt.geom.Point2D;

public class Gemoetry2DUtils {

	public static double area(Point2D[] polyPoints) {
		int i, j, n = polyPoints.length;
		double area = 0;

		for (i = 0; i < n; i++) {
			j = (i + 1) % n;
			area += polyPoints[i].getX() * polyPoints[j].getY();
			area -= polyPoints[j].getX() * polyPoints[i].getY();
		}
		area /= 2.0;
		return (area);
	}

	public static Point2D centerOfMass(Point2D[] polyPoints) {
		double cx = 0, cy = 0;
		double area = area(polyPoints);
		// could change this to Point2D.Float if you want to use less memory
		Point2D res = new Point2D.Double();
		int i, j, n = polyPoints.length;

		double factor = 0;
		for (i = 0; i < n; i++) {
			j = (i + 1) % n;
			factor = (polyPoints[i].getX() * polyPoints[j].getY() - polyPoints[j]
					.getX() * polyPoints[i].getY());
			cx += (polyPoints[i].getX() + polyPoints[j].getX()) * factor;
			cy += (polyPoints[i].getY() + polyPoints[j].getY()) * factor;
		}
		area *= 6.0f;
		factor = 1 / area;
		cx *= factor;
		cy *= factor;
		res.setLocation(cx, cy);
		return res;
	}

}
