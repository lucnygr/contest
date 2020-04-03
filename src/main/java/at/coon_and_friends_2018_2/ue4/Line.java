package at.coon_and_friends_2018_2.ue4;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Line extends BaseObjectClass {

	public int row1;

	public int col1;

	public int row2;

	public int col2;

	public int deltaR;

	public int deltaC;

	public double ratio;

	public Line(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.row1 = scanner.nextInt();
		this.col1 = scanner.nextInt();
		this.row2 = scanner.nextInt();
		this.col2 = scanner.nextInt();

		this.deltaR = this.row2 - this.row1;
		this.deltaC = this.col2 - this.col1;
	
		this.ratio = 0.5 / (Math.max(Math.abs(this.deltaR), Math.abs(this.deltaC)));
		
		Logger.debug("(" + this.row1 + " " + this.col1 + ") (" + this.row2 + " " + this.col2 + ") ratio: " + this.ratio);
	}

	public Set<Point> calculatePoints() {
		Set<Point> points = new LinkedHashSet<>();
		
		points.add(new Point(this.row1, this.col1));

		for (double step = 0; step <= 1; step += this.ratio) {
			double rowC = this.row1 + deltaR * step + 0.5;
			double colC = this.col1 + deltaC * step + 0.5;

			Point point = new Point();
			point.row = (int) Math.floor(rowC);
			point.col = (int) Math.floor(colC);
			points.add(point);
		}
		
		points.add(new Point(this.row2, this.col2));
		
		List<Point> pointsAsList = new ArrayList<>(points);
		
		if(pointsAsList.get(0).row != this.row1 || pointsAsList.get(0).col != this.col1) {
			Logger.debug("(" + this.row1 + " " + this.col1 + ") (" + this.row2 + " " + this.col2 + ") ratio: " + this.ratio);
			throw new RuntimeException();
		}
		if(pointsAsList.get(pointsAsList.size()-1).row != this.row2 || pointsAsList.get(pointsAsList.size()-1).col != this.col2) {
			System.out.println(pointsAsList);
			throw new RuntimeException();
		}

		return points;
	}
}
