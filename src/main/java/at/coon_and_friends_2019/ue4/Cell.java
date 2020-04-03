package at.coon_and_friends_2019.ue4;

import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Cell extends BaseObjectClass implements Comparable<Cell> {

	public int x;

	public int y;

	public long height;

	long countryId;

	Plan plan;
	
	double dinstanceToCenterOfMass;
	
	public Cell(Plan plan, int x, int y) {
		this.plan = plan;
		this.x = x;
		this.y = y;
	}
	
	public Cell(Plan plan, int x, int y, Scanner scanner) {
		this.plan = plan;
		this.x = x;
		this.y = y;
		this.height = scanner.nextLong();
		countryId = scanner.nextLong();
	}
	
	public Long getCountryId() {
		return this.countryId;
	}
	
	public Double getDistanceToCenterOfMass() {
		return this.dinstanceToCenterOfMass;
	}
	
	public boolean isBorderCell() {
		try {
			Cell neighbor = plan.grid[this.x][this.y - 1];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x][this.y + 1];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x - 1][this.y];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
			neighbor = this.plan.grid[this.x + 1][this.y];
			if (neighbor.countryId != this.countryId) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			return true;
		}

		return false;
	}
	
	public void calcDinstanceToCenterOfMass(int x, int y) {
		this.dinstanceToCenterOfMass = Math.sqrt(Math.abs(this.x - x) * Math.abs(this.x - x) + Math.abs(this.y - y) * Math.abs(this.y - y));
	}
	
	// LINE/RECTANGLE
	boolean hasCollisionWithCell(double x1, double y1, double x2, double y2) {

	  // check if the line has hit any of the rectangle's sides
	  // uses the Line/Line function below
	  boolean left =   lineLine(x1,y1,x2,y2, x-0.5,y-0.5,x-0.5, y+0.5);
	  boolean right =  lineLine(x1,y1,x2,y2, x+0.5,y-0.5,x+0.5, y+0.5);
	  boolean top =    lineLine(x1,y1,x2,y2, x-0.5,y-0.5,x+0.5, y-0.5);
	  boolean bottom = lineLine(x1,y1,x2,y2, x-0.5,y+0.5,x+0.5, y+0.5);

	  // if ANY of the above are true, the line
	  // has hit the rectangle
	  if (left || right || top || bottom) {
	    return true;
	  }
	  return false;
	}
	
	// LINE/LINE
	boolean lineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

	  // calculate the direction of the lines
	  double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
	  double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

	  // if uA and uB are between 0-1, lines are colliding
	  if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {

	    // optionally, draw a circle where the lines meet
	    double intersectionX = x1 + (uA * (x2-x1));
	    double intersectionY = y1 + (uA * (y2-y1));

	    return true;
	  }
	  return false;
	}
	
	@Override
	public int compareTo(Cell o) {
		int result = this.y - o.y;
		if(result != 0) {
			return result;
		}
		return this.x- o.x;
	}
	
	public void printToStringBuilder(StringBuilder sb) {
		sb.append(this.x).append(" ").append(this.y);
	}

}
