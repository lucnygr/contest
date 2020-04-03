package at.coon_and_friends_2018_2.ue2;

import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Line extends BaseObjectClass {
	
	public int row1;
	
	public int col1;
	
	public int row2;
	
	public int col2;
	
	public double ratio;

	public Line(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		this.row1 = scanner.nextInt();
		this.col1 = scanner.nextInt();
		this.row2 = scanner.nextInt();
		this.col2 = scanner.nextInt();
		String ratioAsString = scanner.next();
		this.ratio = Double.parseDouble(ratioAsString);
	}
	
	public void calculatePoint(StringBuilder builder) {
		int deltaR = this.row2 - this.row1;
		int deltaC = this.col2 - this.col1;
		
		double rowC = this.row1 + deltaR * this.ratio + 0.5;
		double colC = this.col1 + deltaC * this.ratio + 0.5;
		
		builder.append((int)Math.floor(rowC)).append(" ").append((int)Math.floor(colC));
	}
}
